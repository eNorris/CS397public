package util;

public class SpringEq {

	public double mass = 0.0;
	public double dampen = 0.0;
	public double spring = 0.0;
	public double A = 0.0;

	public double getCriticalDampening(){
		return 2 * Math.sqrt(spring * mass);
	}

	public double getR1(){
		return dampen/(2 * mass) * (-1 + Math.sqrt(1 - (4 * spring * mass)/(dampen * dampen)));
	}

	public double getR2(){
		return dampen/(2 * mass) * (-1 - Math.sqrt(1 - (4 * spring * mass)/(dampen * dampen)));
	}

	public double overdampen(double time){
		return A*Math.exp(getR1()*time) - A*Math.exp(getR2()*time);
	}

	public double underdampen(double time){
		double mu = (Math.sqrt(4*spring*mass - dampen*dampen)) / (2*mass);
		return A * Math.exp(-dampen*time/(2*mass))*Math.sin(mu * time);
	}

	public double critdampen(double time){
		return A*time*Math.exp(-dampen*time/(2*mass));
	}

	public boolean isCriticallyDampened(){
		double crit = getCriticalDampening();
		return (dampen < crit+0.01 && dampen > crit-0.01);
	}

	public boolean isSubDampened(){
		return true;
	}

}