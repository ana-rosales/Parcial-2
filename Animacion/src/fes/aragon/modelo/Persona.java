package fes.aragon.modelo;

public class Persona {

// Atributos:
	private String nombre;
	private float peso;
	private float estatura;
	private int edad;

	public Persona() {
		// TODO Auto-generated constructor stub
	}

	public Persona(String nombre, float peso, float estatura, int edad) {
		this.nombre = nombre;
		this.peso = peso;
		this.estatura = estatura;
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String strings) {
		this.nombre = strings;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public float getEstatura() {
		return estatura;
	}

	public void setEstatura(float estatura) {
		this.estatura = estatura;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Persona {" + "Nombre=" + nombre + ", Peso=" + peso + "kg" + ", Estatura=" + estatura + "m" + ", Edad="
				+ edad + "años" + '}';
	}

	// METODO

	public static String NombresAleatorios() {

		String nombre;
		String[] nombres = { "Danna", "Ana", "Pedro", "Paola", "Esmeralda",
				"Erik" + "Emiliano" + "Alan" + "Jesus" + "David" + "Julio" + "Cassandra" + "Irisol" };
		String[] apellidos = { "Castillo", "Sosa", "Fernandez", "Alba", "Ramirez", "Rosales" + "Olguin",
				"Villalva" + "Velazquez", "Montiel" };

		for (int i = 0; i < 9; i++)
			;
		return nombre = nombres[(int) (Math.floor(Math.random() * ((nombres.length - 1) - 0 + 1) + 0))] + " "
				+ apellidos[(int) (Math.floor(Math.random() * ((apellidos.length - 1) - 0 + 1) + 0))];
	}

}