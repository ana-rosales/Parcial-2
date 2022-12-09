package fes.aragon.controlador;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fes.aragon.modelo.Persona;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class SampleController implements Initializable {
	Data<String, Number> primero = null;
	Data<String, Number> segundo = null;
	int tiempoRetardo = 20, numeroDatos = 100, min = 3, max = 100, min1 = 1, max1 = 2;
	Persona persona[] = new Persona[numeroDatos];

	@FXML
	private BarChart<String, Number> area;

	@FXML
	private Button btnSalir;

	@FXML
	private Button burbuja;

	@FXML
	private Button insercion;

	@FXML
	private Button mezcla;

	@FXML
	private Button quicksort;

	@FXML
	private Button seleccion;

	@FXML
	private Button sinOrdenar;

	/**
	 * Crea las barritas de diferentes largos
	 * 
	 * @param event
	 */
	@FXML
	void aleatorios(ActionEvent event) {
		area.getData().clear();
		Series<String, Number> series = new Series<String, Number>();
		series = generarAleatoriosEnteros(persona);
		area.getData().add(series);
	}

	/**
	 * Acción de botón burbuja, manda a llamar la animación del método.
	 * 
	 * @param event
	 */
	@FXML
	void burbuja(ActionEvent event) {
		this.sinOrdenar.setDisable(true);
		Task<Void> animateSortTask = burbujaTask(area.getData().get(0));
		exec.submit(animateSortTask);
	}

	/**
	 * Se sale de la app.
	 * 
	 * @param event
	 */
	@FXML
	void eventoSalir(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void insercion(ActionEvent event) {

	}

	@FXML
	void mezcla(ActionEvent event) {

	}

	@FXML
	void quicksort(ActionEvent event) {

	}

	/**
	 * Acción del botón selección, manda a llamar la animación del método.
	 * 
	 * @param event
	 */
	@FXML
	void seleccion(ActionEvent event) {
		this.sinOrdenar.setDisable(true);
		Task<Void> animateSortTask = seleccionTask(area.getData().get(0));
		exec.submit(animateSortTask);
	}

	/*
	 * Inicializa la vista: [✓] Inicializa el gráfico sin animación. [✓] Crea las
	 * series (barritas de el gráfico). [✓] Asigna valores aleatorios a las series.
	 * [✓] Y las agrega al gráfico.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (int i = 0; i < persona.length; i++) {
			persona[i] = new Persona(Persona.NombresAleatorios(), (float) (Math.random() * (max - min) + min),
					(float) (Math.random() * (max1 - min1) + min1), (int) (Math.random() * (max - min) + min));
			System.out.println(persona[i]);
		}

		area.setAnimated(false);
		Series<String, Number> series = new Series<String, Number>();
		series = generarAleatoriosEnteros(persona);
		area.getData().add(series);
	}

	/**
	 * Genera n barras de largos distintos para la serie a imprimir.
	 * 
	 * @param n
	 * @return
	 */
	private Series<String, Number> generarAleatoriosEnteros(Persona arreglo[]) {
		Series<String, Number> series = new Series<>();
		for (int i = 1; i <= numeroDatos-1; i++) { // (string i, edad de la persona)
			series.getData().add(new Data<>(String.valueOf(i), arreglo[i].getEdad()));
		}
		return series;
	}

	/**
	 * Método burbuja con animación.
	 * 
	 * @param series
	 * @return
	 */
	private Task<Void> burbujaTask(Series<String, Number> series) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				ObservableList<Data<String, Number>> data = series.getData();
				for (int i = data.size() - 1; i >= 0; i--) {
					for (int j = 0; j < i; j++) {
						primero = data.get(j);
						segundo = data.get(j + 1);

						Platform.runLater(() -> {
							primero.getNode().setStyle("-fx-background-color: red ;");
							segundo.getNode().setStyle("-fx-background-color: blue ;");
						});
						Thread.sleep(tiempoRetardo);
						if (primero.getYValue().doubleValue() >= segundo.getYValue().doubleValue()) {
							CountDownLatch latch = new CountDownLatch(1);
							Platform.runLater(() -> {
								Animation swap = createSwapAnimation(primero, segundo);
								swap.setOnFinished(e -> latch.countDown());
								swap.play();
							});
							latch.await();
						}
						Thread.sleep(tiempoRetardo);
						Platform.runLater(() -> {
							primero.getNode().setStyle("-fx-background-color: blue ;");
							segundo.getNode().setStyle("-fx-background-color: red ;");
						});
					}
				}
				sinOrdenar.setDisable(false);
				verData(persona);
				return null;
			}

		};
	}

	/**
	 * Método selección con animación.
	 * 
	 * @param series
	 * @return
	 */
	private Task<Void> seleccionTask(Series<String, Number> series) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				ObservableList<Data<String, Number>> data = series.getData();
				int min = 0;
				for (int i = 0; i < data.size(); i++) {
					min = i;
					for (int j = i + 1; j < data.size(); j++) {
						primero = data.get(j);
						segundo = data.get(min);
						Platform.runLater(() -> {
							primero.getNode().setStyle("-fx-background-color: red ;");
							segundo.getNode().setStyle("-fx-background-color: blue ;");
						});
						Thread.sleep(tiempoRetardo);
						if (primero.getYValue().doubleValue() < segundo.getYValue().doubleValue()) {
							min = j;
						}
					}
					if (i != min) {
						primero = data.get(i);
						segundo = data.get(min);

						CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(() -> {
							Animation swap = createSwapAnimation(primero, segundo);
							swap.setOnFinished(e -> latch.countDown());
							swap.play();
						});
						latch.await();
						Thread.sleep(tiempoRetardo);
						Platform.runLater(() -> {
							primero.getNode().setStyle("-fx-background-color: blue ;");
							segundo.getNode().setStyle("-fx-background-color: red ;");
						});

					}
				}
				sinOrdenar.setDisable(false);
				verData(persona);
				return null;
			}
		};
	}

	/**
	 * Método en el que se intercambian, gráficamente las barras.
	 * 
	 * @param <T>
	 * @param primero
	 * @param segundo
	 * @return
	 */
	private <T> Animation createSwapAnimation(Data<?, T> primero, Data<?, T> segundo) {
		double primeroX = primero.getNode().getParent().localToScene(primero.getNode().getBoundsInParent()).getMinX();
		double segundoX = primero.getNode().getParent().localToScene(segundo.getNode().getBoundsInParent()).getMinX();

		double primeroStartTranslate = primero.getNode().getTranslateX();
		double segundoStartTranslate = segundo.getNode().getTranslateX();

		TranslateTransition primeroTranslate = new TranslateTransition(Duration.millis(tiempoRetardo),
				primero.getNode());
		primeroTranslate.setByX(segundoX - primeroX);
		TranslateTransition segundoTranslate = new TranslateTransition(Duration.millis(tiempoRetardo),
				segundo.getNode());
		segundoTranslate.setByX(primeroX - segundoX);
		ParallelTransition translate = new ParallelTransition(primeroTranslate, segundoTranslate);

		translate.statusProperty().addListener((obs, oldStatus, newStatus) -> {
			if (oldStatus == Animation.Status.RUNNING) {
				T temp = primero.getYValue();
				primero.setYValue(segundo.getYValue());
				segundo.setYValue(temp);
				primero.getNode().setTranslateX(primeroStartTranslate);
				segundo.getNode().setTranslateX(segundoStartTranslate);
			}
		});

		return translate;
	}

	/**
	 * Ejecuta animaciones para cada método de ordenamiento.
	 */
	private ExecutorService exec = Executors.newCachedThreadPool(runnable -> {
		Thread t = new Thread(runnable);
		t.setDaemon(true);
		return t;
	});

	public static void verData(Persona[] arreglo) {

		for (int i = 0; i < arreglo.length; i++) {
			System.out.println(arreglo[i] + " ");
		}
		System.out.println("");
		System.out.println("------------------");

	}

}
