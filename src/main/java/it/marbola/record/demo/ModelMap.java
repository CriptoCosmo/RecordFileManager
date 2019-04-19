package it.marbola.record.demo;

import it.marbola.record.annotation.Filler;
import it.marbola.record.annotation.Definition;

public class ModelMap {

	@Definition(name="id", size=2)
	private String id ;

	@Definition(name="codiceFiscale", size=16)
	private String codiceFiscale ;

	@Definition(name="sequence", size=12)
	private String sequence ;

	@Definition(name="number", size=10)
	private String number ;

	@Definition(size=4)
	private Filler filler ;

	@Definition(name="date", size=10)
	private String date ;

	@Definition(size=4)
	private Filler filler2 ;

	@Definition(name="note", size=6)
	private String note ;

}
