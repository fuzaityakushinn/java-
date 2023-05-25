package type;

public class DoubleBedsRoom extends Hotel {

    public DoubleBedsRoom(String type, int id, double price) {
        setType(type);
        setId(id);
        setPrice(price);
    }
    @Override
    public double getPayMoney(int days) {
        double money = this.getPrice() * days;
        if (getBreakfast()) {
            money += 100 * days;
        }
        if(days > 3) {
            money = money * 0.8;
        }

        return money;
    }
}
