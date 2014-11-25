// @Harness: v2-parse
// @Result: PASS

class decl1 {
  field a: type;
  field b: int = 0;
  field c: int = 0 + 0;
  field d: int = func(0,0);
  field e: int[] = {1,2,3};
  field f: int[] = { func(0), func(1), func(2) };
}
