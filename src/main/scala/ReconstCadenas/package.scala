import ArbolSufijos ._
import Oraculo ._

package object ReconstCadenas {
  def reconstruirCadenaIngenuo(n:Int, o:Oraculo):Seq[Char] ={
    def buscar(actual: Seq[Char]): Option[Seq[Char]] = {
      if(actual.length == n){
        if(o(actual)) Some(actual)
        else None
      } else {
        val intentos = alfabeto.map { letra =>
          buscar(actual :+ letra)
        }
        intentos.collectFirst { case Some(resultado) => resultado}
      }
    }

    buscar(Seq()).get
  }
  /*
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
  }*/
}