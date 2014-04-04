package com.mansur.interview

import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import java.io.File

/**
 * @author Mansur Ashraf
 */
class ArrayHopperTest extends Specification {

  "Calculate Hop method" should {


    "find the shortest hop" in new Fixture {
      hopper.calculateHops(List(3, 2, 3, 1, 5, 4)) === Right(List(0, 2, 5))
      hopper.calculateHops(List(5, 6, 0, 4, 2, 4, 1, 0, 0, 4)) === Right(List(0, 5, 9))
      hopper.calculateHops(List(4, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)) === Right(List(0, 1, 12))
      hopper.calculateHops(List(4, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)) === Right(List(0, 1, 7, 8, 9, 10, 11, 12))
    }

    "fail if there are no availble paths" in new Fixture {
      hopper.calculateHops(List(0, 2, 3, 1, 5, 4)) === Left("failure")
      hopper.calculateHops(List(3, 0, 0, 0, 5, 4)) === Left("failure")
    }
  }

  "Execute method" should {
    "find the shortest hop" in new Fixture {
      hopper.execute(Array(getPath("/sample1.txt"))).get === "0,1,12, out"
    }

    "fail if no arg is passed" in new Fixture {
      hopper.execute(Array.empty).failed.get.toString === "java.lang.ArrayIndexOutOfBoundsException: 0"
    }

    "fail if input file is empty" in new Fixture {
      hopper.execute(Array(getPath("/empty.txt"))).failed.get.toString === "java.lang.IllegalArgumentException: input file is empty!"
    }
  }

  private def getPath(name: String): String = new File(getClass.getResource(name).toURI).getAbsolutePath
}

trait Fixture extends Scope {
  val hopper = new ArrayHopper()
}
