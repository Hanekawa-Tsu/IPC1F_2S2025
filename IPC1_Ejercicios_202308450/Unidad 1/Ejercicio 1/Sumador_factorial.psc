Algoritmo Sumador_factorial
	Escribir "Introduce un numero entero positivo"
	Leer n

	si n > 0 Entonces
		i <- 1
		factorial <- 0
		Mientras i <= n Hacer
			factorial  <- factorial + i
			i <- i+1
		FinMientras
		
		Escribir "El resultado de la suma factorial es:", factorial
		
	FinSi
	
	
FinAlgoritmo
