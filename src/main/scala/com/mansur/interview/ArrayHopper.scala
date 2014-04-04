package com.mansur.interview

import scala.annotation.tailrec
import scala.util.{Success, Failure, Try}
import scala.io.Source._

/**
 * @author Mansur Ashraf
 */
class ArrayHopper() {

  def execute(args: Array[String]): Try[String] = {
    Try {
      val lines = fromFile(args(0)).getLines()
      if (lines.isEmpty) throw new IllegalArgumentException("input file is empty!")
      val input = lines.map(_.toInt).toList

      calculateHops(input) match {
        case Left(x) => x
        case Right(x) => x.mkString(",") + ", out"
      }
    }
  }

  def calculateHops(input: List[Int]): Either[String, List[Int]] = input match {
    case x :: Nil => Right(List(0))
    case list@x :: xs => traverse(list, Right(List(0)))
  }

  @tailrec
  protected final def traverse(input: List[Int], result: Either[String, List[Int]]): Either[String, List[Int]] = result match {
    case left@Left(_) => left
      case Right(x) =>
        val index = x.head
        val currentValue = input(index)
        val availableHops = index + currentValue
        availableHops match {
          case m if m == index => Left("failure")
          case m if m >= input.size - 1 => Right(((input.size - 1) :: x).reverse)
          case m => {
            val nextHop = input.view.zipWithIndex.slice(index + 1, m + 1).maxBy(x => x._1 + x._2)
            traverse(input, Right(nextHop._2 :: x))
          }
        }
    }
}

object ArrayHopper {

  def main(args: Array[String]) = new ArrayHopper().execute(args)
  match {
    case Failure(e) => println("unable to proceed due to following exception! " + e)
    case Success(x) => println(x)
  }
}
