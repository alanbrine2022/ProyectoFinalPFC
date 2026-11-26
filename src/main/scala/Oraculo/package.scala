package object Oraculo {
  val alfabeto=('a', 'c', 'g', 't')
  type Oraculo = Seq[Char] => Boolean
  
  def crearOraculo(delay:Int)(c:Seq[Char]):Oraculo ={
    def esSubcadena(s:Seq[Char]):Boolean= {
      Thread.sleep(delay)
      c.containsSlice(s)
    }
    esSubcadena
  }
}