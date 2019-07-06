class Main inherits IO {
  fibo0(i:Int):Int {0};
  fibo1(i:Int):Int {1};
  fibo(i:Int):Int {fibo0(i-1) + fibo1(i-2)};

  
    
  main():Object {
    out_int(fibo(2))
  };
};
