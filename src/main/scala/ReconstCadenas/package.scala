import ArbolSufijos ._
import Oraculo ._

package object ReconstCadenas {
  def reconstruirCadenaIngenuo(n:Int, o:Oraculo):Seq[Char] ={
    //Recibe la longitud de la secuencia a reconstruir y el Oraculo para esa cadena

  }
  
  def reconstruirCadenaMejorado(n:Int, o:Oraculo):Seq[Char] ={
    //Usa la propiedad si s=s1++s2 entonces s1 y s2 son subcadenas de s

  }

  def reconstruirCadenaTurbo(n:Int, o:Oraculo):Seq[Char] ={
    //recibe la longitud de la secuencia (n, potencia de 2) 
  }

  def reconstruirCadenaTurboMejorada(n:Int, o:Oraculo):Seq[Char] ={
    //Usa el filtro para ser más rapido
  }

  def reconstruirCadenaTurboAcelerada(n:Int, o:Oraculo):Seq[Char] ={
    //Usa el filtro y arboles de sufijos
  }
}