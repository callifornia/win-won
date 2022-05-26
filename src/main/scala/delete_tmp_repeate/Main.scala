package delete_tmp_repeate


case class Serialized[T](value: T)
case class Foo(age: Int)
case class C(name: String)
case class B(c: C)
case class A(b: B)



trait Serialize[T] {
  def serialize(value: T): Serialized[T]
}

trait Deserialize[T] {
  def deserialize(value: Serialized[T]): T
}

extension [T] (c: T)
  def serialize()(using serialization: Serialize[T]): Serialized[T] =
    serialization.serialize(c)

extension [T] (c: Serialized[T])
  def deserialize()(using deserialization: Deserialize[T]): T =
    deserialization.deserialize(c)

given aSerialization: Serialize[A] with {
  def serialize(value: A): Serialized[A] = {
    Serialized(value)
  }
}

given bSerialization: Serialize[B] with {
  def serialize(value: B): Serialized[B] = Serialized(value)
}

given cSerialization: Serialize[C] with {
  def serialize(value: C): Serialized[C] = Serialized(value)
}

given fooSerialization: Serialize[Foo] with {
  override def serialize(value: Foo): Serialized[Foo] =
    Serialized(value)
}






given fooDeserialization: Deserialize[Foo] with {
  def deserialize(serializedValue: Serialized[Foo]): Foo =
    serializedValue.value
}

given aDeserialization: Deserialize[A] with {
  def deserialize(serializedValue: Serialized[A]): A =
    serializedValue.value
}

given bDeserialization: Deserialize[B] with {
  def deserialize(serializedValue: Serialized[B]): B =
    serializedValue.value
}

given cDeserialization: Deserialize[C] with {
  def deserialize(serializedValue: Serialized[C]): C =
    serializedValue.value
}


object Main {

  @main def run(): Unit = {
    val foo = Foo(123)
    val serializedFoo: Serialized[Foo] = foo.serialize()
    val deSerializedFoo: Foo = serializedFoo.deserialize()

    val aSerialized: Serialized[A] = A(B(C("some value here"))).serialize()

    println(s"aSerialized: $aSerialized")


    println(s"serialized: $serializedFoo")
    println(s"deSerializedFoo: $deSerializedFoo")
  }
}
