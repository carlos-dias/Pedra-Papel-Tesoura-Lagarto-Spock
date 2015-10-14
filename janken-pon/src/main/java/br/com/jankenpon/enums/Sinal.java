package br.com.jankenpon.enums;

public enum Sinal {
	PEDRA {
		@Override
		public boolean ganha(Sinal sinal) {
			return sinal == Sinal.LAGARTO || sinal == Sinal.TESOURA;
		}

		@Override
		public boolean perde(Sinal sinal) {
			return sinal == Sinal.SPOCK || sinal == Sinal.PAPEL;
		}

	},
	PAPEL {
		@Override
		public boolean ganha(Sinal sinal) {
			return sinal == Sinal.PEDRA || sinal == Sinal.SPOCK;
		}

		@Override
		public boolean perde(Sinal sinal) {
			return sinal == Sinal.TESOURA || sinal == Sinal.LAGARTO;
		}

	},
	TESOURA {
		@Override
		public boolean ganha(Sinal sinal) {
			return sinal == Sinal.LAGARTO || sinal == Sinal.PAPEL;
		}

		@Override
		public boolean perde(Sinal sinal) {
			return sinal == Sinal.SPOCK || sinal == Sinal.PEDRA;
		}

	},
	LAGARTO {
		@Override
		public boolean ganha(Sinal sinal) {
			return sinal == Sinal.SPOCK || sinal == Sinal.PAPEL;
		}

		@Override
		public boolean perde(Sinal sinal) {
			return sinal == Sinal.TESOURA || sinal == Sinal.PEDRA;
		}

	},
	SPOCK {
		@Override
		public boolean ganha(Sinal sinal) {
			return sinal == Sinal.TESOURA || sinal == Sinal.PEDRA;
		}

		@Override
		public boolean perde(Sinal sinal) {
			return sinal == Sinal.LAGARTO || sinal == Sinal.PAPEL;
		}

	};

	public abstract boolean ganha(Sinal sinal);

	public abstract boolean perde(Sinal sinal);

}
