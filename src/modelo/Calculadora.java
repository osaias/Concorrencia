package modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Calculadora {

	Map<String, List<BigDecimal>> mapAdicao = new ConcurrentHashMap<>();
	Map<String, List<BigDecimal>> mapSubtracao = new ConcurrentHashMap<>();
	Map<String, List<Double>> mapValorPorNumero = new ConcurrentHashMap<>();
	List<Double> valores = Arrays.asList(10.0, 11.0, 100.0, 20.0, 22.0, 3.0, 4.0, 55.0, 55.0);
	
	public void processar() {
		int i = 0;
		for (Double valor : valores) {
			Tarefa tarefa = new Tarefa(Meta.CLASSSIFICAR, valor);
			Thread classificacao = new Thread(tarefa, "Classificação Nº".concat(String.valueOf(++i)));
			try {
				classificacao.start();
			} catch (Exception e) {
				System.out.println("Classificação: " +e.getMessage());
			}
		}
		
		imprimir(mapValorPorNumero);
	}

	private void imprimir(Map<String, List<Double>> mapa) {
		System.out.println("\nImprimindo...");
		if (mapa.isEmpty())
			System.out.println("Mapa Vazio");
		
		for (Entry<String, List<Double>> entrada : mapa.entrySet()) {
			System.out.print(entrada.getKey() + ": ");
			List<Double> valores = entrada.getValue();
			for (Double valor : valores) {
				System.out.print(valor + " ");
			}
			System.out.println();
		}
		
	}

	public class Tarefa implements Runnable {
		
		private Meta meta;
		private Double valor;
		public Tarefa(Meta meta, Double valor) {
			this.meta = meta;
			this.valor = valor;
			//criar um Map Interno
		}
		
		@Override
		public void run() {
			if (Meta.CLASSSIFICAR.equals(meta)) {
				System.out.println("Executando a Thread: " +Thread.currentThread().getName());
				String chave = valor.toString().substring(0, 1);
				addNoMapValorPorNumero(chave, valor);
			}
			
		}
		
		private void addNoMapValorPorNumero(String chave, Double valor) {
			if (!mapValorPorNumero.containsKey(chave)) {
				mapValorPorNumero.put(chave, new ArrayList<Double>());
			}
			mapValorPorNumero.get(chave).add(valor);
		}
		
	}
	
	public enum Meta{
		CLASSSIFICAR, SOMAR, SUBTRAIR, MULTIPLICAR, DIVIDIR
	}
}
