let isDone: boolean = false
let hello: string = "true"
if (isDone) {
    hello = "true"
} else {
    hello = "false"
}

let decLiteral: number = 6;
let hexLiteral: number = 0xf00d;
let binaryLiteral: number = 0b1010;
let octalLiteral: number = 0o744;

let strName: string = 'bob';
strName = 'smith';

let geneName: string = `Gene`;
let age: number = 37;
let sentence: string = `Hello, my name is ${geneName}.

I'll be ${age + 1} years old next month.`;


let list: number[] = [1, 2, 3];
let list2: Array<number> = [1, 2, 3];

// Declare a tuple type
let x: [string, number];
// Initialize it
x = ['hello', 10]; // OK
// Initialize it incorrectly
// x = [10, 'hello']; // Error

console.log(x[0].substr(1)); // OK
// console.log(x[1].substr(1)); // Error, 'number' does not have 'substr'

enum Color {Red, Green, Blue}

let c: Color = Color.Green;

enum Color2 {Red = 1, Green, Blue}

let c2: Color2 = Color2.Green;

enum Color3 {Red = 1, Green = 2, Blue = 4}

let c3: Color3 = Color3.Green;

enum Color4 {Red = 1, Green, Blue}

let colorName: string = Color4[2];

console.log(colorName);  // 显示'Green'因为上面代码里它的值是2

let notSure: any = 4;
notSure = "maybe a string instead";
notSure = false; // okay, definitely a boolean
document.body.innerHTML = hello;
