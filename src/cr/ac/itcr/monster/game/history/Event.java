package cr.ac.itcr.monster.game.history;

public class Event {
    private String info;

    private Event next;
    private Event prev;

    public Event(String data) {
        this.info = data;
    }

    public String getInfo() {
        return info;
    }

    public Event getNext() {
        return next;
    }

    public void setNext(Event next) {
        this.next = next;
    }

    public Event getPrev() {
        return prev;
    }

    public void setPrev(Event prev) {
        this.prev = prev;
    }
}
