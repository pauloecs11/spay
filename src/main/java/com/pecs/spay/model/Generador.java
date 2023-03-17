package com.pecs.spay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="generador")
public class Generador {
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int llave;

		public int getLlave() {
			return llave;
		}

		public void setLlave(int llave) {
			this.llave = llave;
		}

		public Generador(int llave) {
			this.llave = llave;
		}

		public Generador() {
		}

		@Override
		public String toString() {
			return "GeneradorAuto [llave=" + llave + "]";
		}
		
		
		
}
