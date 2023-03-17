package com.pecs.spay.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pecs.spay.model.Generador;
import com.pecs.spay.model.TransaccionPC;
import com.pecs.spay.service.GeneradorService;
import com.pecs.spay.service.TransaccionPCService;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
//import cl.transbank.model.CardDetail;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
//import cl.transbank.webpay.webpayplus.WebpayPlus.Transaction;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;


@Controller
public class HomeController {

	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private GeneradorService generadorService;
	
	@Autowired
	private TransaccionPCService transaccionPCService;
		
	@GetMapping({"index",""})
	public String home() {
		return "index";
	}
	
	@GetMapping("error")
	public String error() {
		return "error";
	}
	
	@GetMapping("carrito")
	public String carrito(Model modelo) {
		//Genero una llave con la tabla generador.
		Generador generador1 = new Generador();
		generadorService.save(generador1);
		//Con la llave, genero el sessionId y el BuyOrder.
		Integer llave= generador1.getLlave();
		String sessionId= "sesion"+llave;
		String buyOrder="orden"+llave;
		String returnUrl="http://localhost:8080/confirmar";
		//El monto de la transaccion sera casi 60.
		Double amount=	59990.00;	
		modelo.addAttribute("returnUrl",returnUrl);
		modelo.addAttribute("amount",amount);
		modelo.addAttribute("sessionId",sessionId);
		modelo.addAttribute("buyOrder",buyOrder);
		return "carrito";
	}
	
	@PostMapping("pagar")
	public String pagar(String buyOrder,String sessionId,Double amount,String returnUrl,Model modelo) {
		//Obtengo los datos de formulario con post.
		WebpayOptions webpayOptions= new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS,IntegrationApiKeys.WEBPAY, IntegrationType.TEST);
		WebpayPlus.Transaction tx = new WebpayPlus.Transaction(webpayOptions);
		try {
			final WebpayPlusTransactionCreateResponse response = tx.create(buyOrder, sessionId, amount, returnUrl);
			String tbkForm= response.getUrl();
			String token =response.getToken();
			return "redirect:"+tbkForm+"?token_ws="+token;
			
		}catch(TransactionCreateException | IOException ex) {
			System.err.println("Error en tx.create(), metodo pagar.");
			ex.printStackTrace();
			return "redirect:error";
		}
		
	}
	
	//Este metodo ahora se encargara solo de hacer el commit a la transaccion.
	//Deberia modificar este metoso para que solo haga el commit, y luego lleve a la pagina del detalle.
	//Osea que esta pagina solo confirme, no haga más operaciones.
	@GetMapping("confirmar")
	public String confirmar(@RequestParam String token_ws,Model modelo){
		
		
		String estatusPago;
		String detalleEstado;
		int idTransaccion=0;
		// Si dos transacciones tienen el mismo token, no deberia procesarse.
		if( transaccionPCService.existByToken(token_ws)) {
			//No se hara el commit, ni se guardara en la base de datos.
			LOGGER.info("Esta transaccion ya esta procesada y almacenada en la base de datos.");
			return "redirect:error";
			
		}else {
			LOGGER.info("Se procedera a confirmar su transaccion.");
		}
		//
		try {
			
			WebpayPlus.Transaction tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
			final WebpayPlusTransactionCommitResponse resp = tx.commit(token_ws);
			
			byte codigo=resp.getResponseCode();
			String status=resp.getStatus();

			if( codigo==0 && status.equals("AUTHORIZED") ) {
				LOGGER.info("Pago confirmado!");
				estatusPago="Pago confirmado!";
				detalleEstado="Todo salio bien.";
			}else {
				LOGGER.info("Algo fallo en el pago, vea las variables del response.");
				estatusPago=("Pago rechazado. :/");
				detalleEstado="Aqui  se mostraran los codigos del error.";
				detalleEstado="Estatus: "+resp.getStatus()+"\n<br>"+"Codigo de error: "+resp.getResponseCode();
			}
			//La pagina solo mostrara si el pago se confirmo o no.
			//A lo mas un codigo de error, y el id de transaccion.
			
			
			//Lo otro que debe hacer la pagina es guardar los datos en la base de datos.
			TransaccionPC transaccionPC=new TransaccionPC();
			transaccionPC.setToken(token_ws);
			transaccionPC.setStatus(status);
			transaccionPC.setResponseCode(codigo);
			transaccionPC.setVci(resp.getVci());
			transaccionPC.setAmount(resp.getAmount());
			transaccionPC.setBuyOrder(resp.getBuyOrder());
			transaccionPC.setSessionID(resp.getSessionId());
			transaccionPC.setCardNumber(resp.getCardDetail().getCardNumber());
			transaccionPC.setAcountingDate(resp.getAccountingDate());
			transaccionPC.setTransactionDate(resp.getTransactionDate());
			transaccionPC.setAuthorizationCode(resp.getAuthorizationCode());
			transaccionPC.setPaymentTypeCode(resp.getPaymentTypeCode());
			transaccionPC.setInstallmentsAmount(resp.getInstallmentsAmount());
			transaccionPC.setInstallmentsNumber(resp.getInstallmentsNumber());
			
			transaccionPCService.update(transaccionPC);
			LOGGER.info("Depues de insertar... transaccionPC = {}",transaccionPC);
			idTransaccion=transaccionPC.getId();
			//Pasar por model solo los datos basicos a la pagina confirmar. (id,estatus y si se autorizo o no.)
			
		} catch (TransactionCommitException | IOException e) {
			System.err.println("Hubo un error al hacer commit de la transacción.");
			e.printStackTrace();
			return "redirect:error";
		}
		
		modelo.addAttribute("estatusPago",estatusPago);
		modelo.addAttribute("detalleEstado",detalleEstado);
		modelo.addAttribute("idTransaccion",idTransaccion);
		return "confirmar";
	}
	
	//Este método mostrara, la info de una transaccion almacenada en la base de datos local.
	@GetMapping("consultar")
	public String consultar(@RequestParam(value="idT",required = false) Integer idT, Model modelo){
		
		if(idT== null) {
			
			modelo.addAttribute("status",null);
			return "consultar";
			
			
		}else
		{
			//Recuperamos la transaccion que tenga esa idT.
			Optional<TransaccionPC> opcionalTransac=transaccionPCService.get(idT);
			if( opcionalTransac.isEmpty()) {
				//No mostrara nada.
			}else {
				TransaccionPC transac = opcionalTransac.get();
				modelo.addAttribute("id",idT);
				modelo.addAttribute("status",transac.getStatus());
				modelo.addAttribute("token",transac.getToken());
				modelo.addAttribute("vci", transac.getVci());
				modelo.addAttribute("amount",transac.getAmount());
				modelo.addAttribute("buyOrder", transac.getBuyOrder());
				modelo.addAttribute("sessionId",transac.getSessionID());
				modelo.addAttribute("cardNumber", transac.getCardNumber());
				modelo.addAttribute("acountingDate", transac.getAcountingDate());
				modelo.addAttribute("transactionDate",transac.getTransactionDate());
				modelo.addAttribute("authorizationCode", transac.getAuthorizationCode());
				modelo.addAttribute("paymentTypeCode",transac.getPaymentTypeCode());
				modelo.addAttribute("responseCode", transac.getResponseCode());
				modelo.addAttribute("installmentsAmount",transac.getInstallmentsAmount());
				modelo.addAttribute("installmentsNumber",transac.getInstallmentsNumber());
				return "consultar";
			}
			//Poner campos vacios
			return "consultar";
		}
		
	}
	
}
