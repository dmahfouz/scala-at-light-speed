package com.rockthejvm

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object Advanced extends App {

  /**
   * lazy evaluation
   */

  // not evaluated until first use
  lazy val aLazyValue = 2

  // Illustrate with side effect
  lazy val lazyValueWithSideEffect = {
    println("I am so very lazy!")
    43
  }

  // Because lazyValueWithSideEffect is now used, it's now evaluated
  val eagerValue = lazyValueWithSideEffect + 1

  // useful in infinite collections

  /**
   "pseudo-collections": Option, Try
   */

  def methodWhichCanReturnNull(): String = "hello, Scala"
  val anOption = Option(methodWhichCanReturnNull()) // Some("hello, Scala")
  // option = "collection" which contains at most one element: Some(value) or None

  val stringProcessing = anOption match {
    case Some(string) => s"I have obtained a valid string $string"
    case None => "I obtained nothing"
  }

  // Option guards against needing to check for NULLs

  // map, flatMap, filter

  // Try: guards against methods which can throw exceptions
  def methodWhichCanThrowException(): String = throw new RuntimeException

  // Typically..
  try {
    methodWhichCanThrowException()
  } catch {
    case e: Exception => "defend against this evil exception"
  }

  // Alternatively can use a Try
  val aTry = Try(methodWhichCanThrowException())
  // a try = "collection" with either a value if the code went well, or an exception if the code threw one

  val anotherStringProcessing = aTry match {
    case Success(validValue) => s"I have obtained a valid string $validValue"
    case Failure(ex) => s"I have obtained an exception: $ex"
  }
  // flatmap, map, filter

  /**
   * Evaluate something on another thread
   * (asynchronous programming)
   */

  val aFuture = Future {  // Future.apply
    println("Loading...")
    Thread.sleep(1000)
    println("I have computed a value")
    67
  }

  // future is a "collection", which contains a value when it's evaluated
  // future is composable with map, flatMap and filter
  // Future, Try and Option are "monads" in Functional Programming

  Thread.sleep(2000)

  /**
   * Implicits basics
   */

  // Use case #1: implicit arguments
  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val myImplicitInt = 46

  println(aMethodWithImplicitArgs)  // aMethodWithImplicitArgs(myImplicitInt)

  // Use case #2: implicit conversions
  implicit class MyRichInteger(n: Int) {
    def isEven() = n % 2 == 0
  }

  // Compiler is able to figure out / find an implicit wrapper and apply to that class (Integer)
  // Powerful, but dangerous! Use with care!
  println(23.isEven())



}
