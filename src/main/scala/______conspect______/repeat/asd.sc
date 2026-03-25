
case class Foo(a: String) extends Nothing

val foo = new Foo("asd")
println(foo.a)

//for {
//  x <- Some("asd")
//  _ = println("asd")
//} yield ???
//
//
//// Actor
//  - State
//  - Behavior
//
//  -