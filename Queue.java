public class Queue<T>
{
    public class Node
    {
        T data;
        Node next;
        Node(T x){
            data = x;
            next = null;
        }
    }
    Node front, rear;
    public Queue(){
        front = rear = null;
    }
    public boolean isEmpty(){
        return (front == null);
    }
    public T deQueue(){
        if(isEmpty())
            System.out.println("Queue Already Empty");
        Node temp = front;
        front = front.next;
        return temp.data;
    }
    public void add(T data)
    {
        Node temp = new Node(data);
        if(isEmpty())
        {
            front = rear = temp;
        }
        else
        {
            rear.next = temp;
            rear = temp;
        }
    }
    public T peek(){
        return front.data;
    }
    public boolean search(Queue q,String key){
        if (q.isEmpty())
            return false;
        boolean flag = false;
        Queue temp = new Queue();
        while (!q.isEmpty()){
            temp.add(q.peek());
            Vertex v = (Vertex) q.deQueue();
            if (v.getName().equals(key))
                flag = true;
        }
        while (!temp.isEmpty()) q.add(temp.deQueue());
        return flag;
    }
}