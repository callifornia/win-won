// variant | contravariant | covariant

trait Parent
class Child extends Parent

class MyList[T]
class MyList_2[+T]
class MyList_3[-T]

trait Vet[-T] {
  def heal(animal: T): Boolean
}

val healed_child = new Vet[Child] {
  override def heal(animal: Child): Boolean = {
    println("Child is healed: " + animal)
    true
  }
}

val child: MyList[Parent]   = new MyList[Parent]
// val child_2: MyList[Parent] = new MyList[Child] // wont compile

val child_3: MyList_2[Parent] = new MyList_2[Child] // wont compile
val child_4: MyList_3[Child] = new MyList_3[Parent] // wont compile


healed_child.heal(new Child)

