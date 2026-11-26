package object ArbolSufijos{
  abstract class Trie
  case class Nodo (car:Char, marcada:Boolean, hijos: List[Trie]) extends Trie
  case class Hoja (car: Char, marcada:Boolean) extends Trie

  def raiz(t: Trie):Char ={
    t match {
      case Nodo(c,_,_) => c
      case Hoja(c,_) => c
    }
  }
}