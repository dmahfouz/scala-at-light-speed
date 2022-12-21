package com.rockthejvm

object ContextualAbstractions {

  /**
   * 1: context parameters/arguments
  */

  val aList = List(2,1,4,3)
  val anOrderedList = aList.sorted // (ordering)

  // Ordering
  given descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _) // (a,b) => a > b

  // analogous to an implicit val

  // given keyword will inject existing methods to aList.sorted
  // i.e. we can change the behaviour of the method

  trait Combinator[A] {  // monoid
    def combine(x: A, y: A): A
  }

  // combineAll(List(1,2,3,4))
  def combineAll[A](list: List[A])(using combinator: Combinator[A]): A =
    list.reduce((a,b) => combinator.combine(a,b))

  given intCombinator: Combinator[Int] = new Combinator[Int] {
    override def combine(x: Int, y: Int): Int = x + y
  }

  val theSum = combineAll(aList)

  /**
   * Given places compiler will look for contextual arguments
   * - local scope
   * - imported score (import yourpackage.given: imports all given instances from yourpackage)
   * - companions of all the types involved in the call
   *  - the companion of List
   *  - the companion of Int
  */

  // context bounds
  def combineAll_v2[A](list: List[A])(using Combinator[A]): A = ???

  // [A : Combinator] indicates A must have a given instance of Combinator in scope
  // same as combineAll_v2
  def combineAll_v3[A : Combinator](list: List[A]): A = ???

  /**
   * Where context args are useful
   * - type classes
   * - dependency injection
   * - context dependent functionality
   * - type-level programming
   */

  /**
   * 2 - extension methods
  */

  case class Person(name: String) {
    def greet(): String = s"Hi, my name is $name, I love Scala!"
  }

  extension (string: String)
    def greet(): String = new Person(string).greet()

  val davidsGreeting = "David".greet()  // "type enrichment"

  // POWER
  extension [A] (list: List[A])
    def combineAllValues(using combinator: Combinator[A]): A =
      list.reduce(combinator.combine)

  val theSum_v2 = aList.combineAllValues

  def main(args: Array[String]): Unit = {
    println(anOrderedList)
    println(theSum)
    println(theSum_v2)
    println(davidsGreeting)


  }

}
