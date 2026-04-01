// public class Lab5_ArrayIntegerStack {
//     public static void main(String[] args) {}
// }

// class ArrayIntegerStack implements IntegerStack {
//     private int capacity;
//     private int top = 0;
//     private Integer[] arrStack;

//     /* 其他代码 */
//     /* 你的答案，即3个方法的代码 */
//     // 如果item为null，则不入栈直接返回null。如果栈满，抛出FullStackException（系统已有的异常类）。
//     public Integer push(Integer item) throws FullStackException {
//         if (item == null)
//             return null;
//         if (top >= capacity)
//             throw new FullStackException();
//         arrStack[top++] = item;
//         return item;
//     }

//     // 出栈。如果栈空，抛出EmptyStackException，否则返回
//     public Integer pop() throws EmptyStackException {
//         if (top == 0)
//             throw new EmptyStackException();
//         return arrStack[--top];
//     }

//     // 获得栈顶元素。如果栈空，抛出EmptyStackException。
//     public Integer peek() throws EmptyStackException {
//         if (top == 0)
//             throw new EmptyStackException();
//         return arrStack[top - 1];
//     }
// }
