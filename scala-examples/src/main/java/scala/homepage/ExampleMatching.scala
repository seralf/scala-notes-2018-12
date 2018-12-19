package scala.homepage

object ExampleMatching extends App {

  val n1 = Node(42, Leaf, Leaf)
  val n2 = Node(12, Leaf, Leaf)
  val n3 = Node(51, n2, n1)
  val n4 = Node(33, Leaf, Leaf)
  val n5 = Node(51, n3, n4)

  println(n5)

  val n5_ord = TreeHelper.inOrder(n5)

  println(n5_ord)

}

// Define a set of case classes for representing binary trees.
sealed abstract class Tree
case class Node(elem: Int, left: Tree, right: Tree) extends Tree
case object Leaf extends Tree

object TreeHelper {

  // Return the in-order traversal sequence of a given tree.
  def inOrder(t: Tree): List[Int] = t match {
    case Node(e, l, r) => inOrder(l) ::: List(e) ::: inOrder(r)
    case Leaf          => List()
  }

}