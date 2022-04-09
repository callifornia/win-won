import munit.FunSuite

class MainSuite extends FunSuite {

  test("check run tests") {
    assert(1 == 1)
  }

  test("check run tests with failed") {
    assert(1 !=  1)
  }
}
