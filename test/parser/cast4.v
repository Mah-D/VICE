// @Harness: v2-parse
// @Result: PASS

component cast4 {
	field f: T = a.f :: T;
	field g: T = a.m() :: T;
	field h: T = g() :: T;
	field i: T = a :: T :: V;
}
