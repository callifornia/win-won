package repeat.main.scala

object ScalaTutorials {
  def main(args: Array[String]): Unit = {
    println("Is everything ok ???????")
  }


  // Variants | Covariant | Contravariant
  /*
  *   Dog is subtype of Animal
  *   Is List[Dog] is subtype of List[Animal] = this is variant question

  *   In case answer is YES         => it means Covariant | In Scala it marked as List[+A] */
        class Animal
        class Dog extends Animal
        val _: List[Animal] = List.empty[Dog]

  /*   In case answer is NO         => it means Invariant where no relationships between List[Animal] and List[Dog]. In Scala it marked as List[A] */
        class MyList[A]
        val _: MyList[Animal] = new MyList[Animal]

  /*    In case answer is HELL NO   => it means Contravariant. Where in Scala it marked as List[-A] */
        class MyList_2[-A]
        val _: MyList_2[Dog] = new MyList_2[Animal]

  /* example */
        trait Vet[-T] {
          def heal(animal: T): Boolean
        }

        val myDogVet: Vet[Dog] = new Vet[Animal] {
          override def heal(animal: Animal): Boolean = {
            println("Dog is healthy")
            true
          }
        }

        myDogVet.heal(new Dog)

  /*
  * Summary
  *   - In case your generic type produce(creates) or contain an elements   =>  COVARIANT
  *   - In case your generic type "acts on" or consume an elements          =>  CONTRAVARIANTx
  * */






  // SELF TYPE
  trait Builder
  trait Hospital { selfType: Builder =>

  }
  /*
  * Which mean everyone who extends Hospital MUST extends Hospital
  *
  * */









  




}




