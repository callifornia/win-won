
// How to return most specific subtype not just a base type
// This type wants to return itself, but safely

/*

  F-bounded polymorphism is perfect for:
    	•	Builders
  	  •	Fluent APIs
  	  •	Clone/duplicate patterns
  	  •	Comparable-like interfaces

* */

trait Animal[A <: Animal[A]] { self: A =>
  def compareTo(other: A): A = ???
}

class Dog extends Animal[Dog]
class Cat extends Animal[Dog]

println("compiled ...")
