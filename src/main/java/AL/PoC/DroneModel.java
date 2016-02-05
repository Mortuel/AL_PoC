package AL.PoC;

public class DroneModel {
	private static int cpt = 0;
	private String[] pdv ;
	private int poidsColis;
	private int x, y;
	private int step;
	private int id;
	private boolean fini, back;
	
	public DroneModel(){
		this.id = cpt++;
	}
	
	public void go(){
		this.step = 0;
		this.fini = false;
		this.back = false;
		
		if(pdv.length <= 0) System.out.println("PDV vide");
		else if(pdv.length == 1) System.out.println("PDV : " + pdv[0]);
		else {
			String res = "PDV : " + pdv[0];
			for(int i = 1 ; i < pdv.length ; i++) {
				res += "/" + pdv[i];
			}
			System.out.println(res);
		}
		this.x = Integer.parseInt(pdv[step].split("-")[0]);
		this.y = Integer.parseInt(pdv[step].split("-")[1]);
		System.out.println("PING : " + this.x + "/" + this.y);
	}
	
	public void nextStep(){
		if(!fini){
			this.step++;
			if(this.step + 1 == pdv.length){
				fini = true;
			}
		} else {
			this.step--;
		}
		this.x = Integer.parseInt(pdv[step].split("-")[0]);
		this.y = Integer.parseInt(pdv[step].split("-")[1]);
		System.out.println("PING : " + this.x + "/" + this.y);
		
		if(this.x == 0 && this.y == 0) {
			if(back && !fini) System.out.println("Livraison interrompue par agent !");
			else {
				System.out.println("Livraison terminee !");
				this.pdv = null;
			}
		}
	}
	
	public void back(){
		this.fini = true;
		this.back = true;
	}
	
	public DroneModel(String[] pdv){
		this.pdv = pdv;
	}
	
	public void setPdv(String[] pdv){
		this.pdv = pdv;
	}
	
	public String[] getPdv(){
		return this.pdv;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPoidsColis() {
		return poidsColis;
	}

	public void setPoidsColis(int poidsColis) {
		this.poidsColis = poidsColis;
	}
	
	public String toString() {
		if(pdv.length <= 0) return "";
		else if(pdv.length == 1) return pdv[0];
		else {
			String res = pdv[0];
			for(int i = 1 ; i < pdv.length ; i++) {
				res += "|" + pdv[i];
			}
			return res;
		}
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
