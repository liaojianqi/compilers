class A {
    a:Int;
    set_a(num:Int) :Object {
	a <- num
    };
};

class B inherits A{
    bark():Object{(new IO).out_int(a).out_string("\n")};
};

class C inherits A{};

class Main {
    a:A;
    class_type(var:A):Object {
	case var of
	    a:A => (new IO).out_string("is A\n");
	    b:B => {(new IO).out_string("is A\n"); b.bark();};
	    c:C => (new IO).out_string("is C\n");
	esac
    };
    main():Object {{
	(new IO).out_string("cc\n");
	a <- new B;
	a.set_a(10);
	self.class_type(a);
    }};
};
