/* Creo que se debe usar otra cosa para medir el tiempo
este archivo solo es provisional y no lo voy a incluir en el proyecto
*/
import ReconstCadenas._
import Oraculo._

def medirTiempo[T](bloque: => T): T = {
    val inicio = System.nanoTime()
    val resultado = bloque
    val fin = System.nanoTime()
    println(s"Tiempo ${(fin - inicio) / 1e6} ms")
    resultado
}

val sec = Seq('a', 'c', 'g', 't', 'a', 'c')
val o = crearOraculo(1)(sec)

medirTiempo(reconstruirCadenaIngenuo(6, o))