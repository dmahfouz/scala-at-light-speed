package com.rockthejvm

object PatternMatching extends App {

  // switch expression
  val anInteger: Int = 55
  val order = anInteger match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => anInteger + "th"
  }

  // Pattern match is an EXPRESSION

  /*
   * Reminder:
   * When creating an instance of a case class, they don't need to be instantiated
   * with the keyword 'new' b/c the person case class has a companion object with
   * the apply method
   */

  // Case class decomposition
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 43) // Person.apply("Bob", 43)

  val personGreeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I'm $a years old."
    case _ => "Something else"
  }
  println(personGreeting)

  // deconstructing tuples
  val aTuple = ("Bon Jovi", "Rock")
  val bandDescription = aTuple match {
    case (band, genre) => s"$band belongs to genre $genre"
    case _ => "I don't know what you're talking about"
  }

  // decomposing lists
  val aList = List(1,2,3)
  val listDescription = aList match {
    case List(_, 2, _) => "List containing 2 on its second position"
    case _ => "Unknown list"

  }

  // if PM doesn't matching anything, it will throw a MatchError
  // PM will try all cases in sequence (i.e. if previous case has match, all following cases will be ignored)


}
