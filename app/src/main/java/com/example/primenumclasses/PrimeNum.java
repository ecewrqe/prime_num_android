package com.example.primenumclasses;
import android.util.Log;

import java.util.Objects;
import java.lang.Integer;
import java.util.*;
public class PrimeNum {
	static public boolean is_prime_func(int n, List<Integer> li) {

		li.set(0, 0);
		if(n == 1) {

			return true;
		}
		for(int i = 2; i < Math.pow(n, 0.5) + 1; i++) {
			if(n % i == 0) {
				li.set(0, i);
				return false;
			}
		}
		return true;
	}

	static public boolean is_happy_func(int n) {
		Set<Integer> squareSum = new HashSet<Integer>();
		while(true) {
			n = numSquareSum(n);
			if(n == 1) return true;
			if(squareSum.contains(n)) return false;
			squareSum.add(n);
		}


	}
	static int numSquareSum(int n) {
		int squareSum = 0;
		while(n > 0) {
			squareSum += (n%10) *(n%10);
			n /= 10;
		}
		return squareSum;
	}

	static public List<Integer> get_primenum_list(int max_num, int min_num, boolean happy, boolean prime) {
		List<Integer> prime_list = new ArrayList<Integer>();
		if(min_num < 7) {
			min_num = 7;
		}
		int num = min_num;
		if(min_num > max_num) {
			return prime_list;
		}
		if(prime) {
			while(num <= max_num) {
				List<Integer> remainder = new ArrayList<Integer>();
				remainder.add(0);
				boolean is_prime = is_prime_func(num, remainder);
				if(is_prime) {
					if(happy && is_happy_func(num)) {
						prime_list.add(num);
					}
					else if(!happy) {
						prime_list.add(num);
					}
				}
				num++;
			}
		}else {
			while(num <= max_num) {
				if(happy && is_happy_func(num)) {
					prime_list.add(num);
				}
				num++;
			}
		}
		return prime_list;
	}
}
