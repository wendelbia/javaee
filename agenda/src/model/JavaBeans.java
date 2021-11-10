package model;

// TODO: Auto-generated Javadoc
/**
 * The Class JavaBeans.
 */
public class JavaBeans {

		/** The idcon. */
		private String idcon;
		
		/** The nome. */
		private String nome;
		
		/** The fone. */
		private String fone;
		
		/** The email. */
		private String email;
		
		/**
		 * Gets the idcon.
		 *
		 * @return the idcon
		 */
		public String getIdcon() {
			return idcon;
		}
		
		/**
		 * Sets the idcon.
		 *
		 * @param idcon the new idcon
		 */
		public void setIdcon(String idcon) {
			this.idcon = idcon;
		}
		
		/**
		 * Gets the nome.
		 *
		 * @return the nome
		 */
		public String getNome() {
			return nome;
		}
		
		/**
		 * Sets the nome.
		 *
		 * @param nome the new nome
		 */
		public void setNome(String nome) {
			this.nome = nome;
		}
		
		/**
		 * Gets the fone.
		 *
		 * @return the fone
		 */
		public String getFone() {
			return fone;
		}
		
		/**
		 * Sets the fone.
		 *
		 * @param fone the new fone
		 */
		public void setFone(String fone) {
			this.fone = fone;
		}
		
		/**
		 * Gets the email.
		 *
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}
		
		/**
		 * Sets the email.
		 *
		 * @param email the new email
		 */
		public void setEmail(String email) {
			this.email = email;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result + ((fone == null) ? 0 : fone.hashCode());
			result = prime * result + ((idcon == null) ? 0 : idcon.hashCode());
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
			return result;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			JavaBeans other = (JavaBeans) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (fone == null) {
				if (other.fone != null)
					return false;
			} else if (!fone.equals(other.fone))
				return false;
			if (idcon == null) {
				if (other.idcon != null)
					return false;
			} else if (!idcon.equals(other.idcon))
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			return true;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "JavaBeans [idcon=" + idcon + ", nome=" + nome + ", fone="
					+ fone + ", email=" + email + "]";
		}
		
		/**
		 * Instantiates a new java beans.
		 *
		 * @param idcon the idcon
		 * @param nome the nome
		 * @param fone the fone
		 * @param email the email
		 */
		public JavaBeans(String idcon, String nome, String fone, String email) {
			super();
			this.idcon = idcon;
			this.nome = nome;
			this.fone = fone;
			this.email = email;
		}
		
		/**
		 * Instantiates a new java beans.
		 */
		public JavaBeans() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
}
