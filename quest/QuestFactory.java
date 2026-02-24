package quest;

import items.BreakerAxe;
import items.GrandPotion;
import items.MirrorShield;
import items.PhoenixOrb;
import items.PowerElixir;
import items.SoulHunter;
import items.SpiritFruit;
import items.SteadfastHelm;
import items.TimeSand;
import items.TitaniumPlate;
import items.VenomDagger;
import items.WarHorn;
import java.util.List;

public class QuestFactory {

    // =========================
    // ⚔️ โจมตี
    // =========================

    public static Quest soulHunter() {
        return new Quest(
                "Soul Hunter",
                "โจมตีแล้วสะสม Soul Stack ครบ 3 ครั้ง → การโจมตีถัดไปแรง x2",
                """
                        • เลือก 3 ช่อง
                        • ผลรวม = 15
                        • ต้องมีเลขคี่อย่างน้อย 1 ช่อง
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    int sum = numbers.stream().mapToInt(i -> i).sum();
                    boolean hasOdd = numbers.stream().anyMatch(n -> n % 2 == 1);

                    return sum == 15 && hasOdd;
                },
                () -> new SoulHunter(), true);
    }

    public static Quest breakerAxe() {
        return new Quest(
                "Breaker Axe",
                "มีโอกาสติดคริติคอล 20% (ดาเมจ x1.5)",
                """
                        • เลือก 2 ช่อง
                        • ผลรวมระหว่าง 10–14
                        • มีเลข ≥ 7 อย่างน้อย 1 ช่อง
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 2)
                        return false;

                    int sum = numbers.stream().mapToInt(i -> i).sum();
                    boolean high = numbers.stream().anyMatch(n -> n >= 7);

                    return sum >= 10 && sum <= 14 && high;
                },
                () -> new BreakerAxe(), true);
    }

    public static Quest venomDagger() {
        return new Quest(
                "Venom Dagger",
                "ทำให้ศัตรูติดพิษ 3 เทิร์น",
                """
                        • เลือก 3 ช่อง
                        • ต้องมีตัวเลขซ้ำ
                        • ผลรวม ≤ 12
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    int sum = numbers.stream().mapToInt(i -> i).sum();
                    boolean dup = numbers.stream().distinct().count() < 3;

                    return dup && sum <= 12;
                },
                () -> new VenomDagger(), true);
    }

    // =========================
    // 🛡 ป้องกัน
    // =========================

    public static Quest mirrorShield() {
        return new Quest(
                "Mirror Shield",
                "มีโอกาสสะท้อนดาเมจ 15%",
                """
                        • เลือก 3 ช่อง
                        • ตัวเลขเรียงติดกัน
                        • ผลรวม ≥ 12
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    numbers.sort(Integer::compareTo);

                    boolean consecutive = numbers.get(1) == numbers.get(0) + 1 &&
                            numbers.get(2) == numbers.get(1) + 1;

                    int sum = numbers.stream().mapToInt(i -> i).sum();

                    return consecutive && sum >= 12;
                },
                () -> new MirrorShield(), true);
    }

    public static Quest titaniumPlate() {
        return new Quest(
                "Titanium Plate",
                "ลดดาเมจที่ได้รับ 20% เป็นเวลา 2 เทิร์น",
                """
                        • เลือกเลขคู่ 3 ช่อง
                        • ผลรวม ≥ 12
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    boolean allEven = numbers.stream().allMatch(n -> n % 2 == 0);

                    int sum = numbers.stream().mapToInt(i -> i).sum();

                    return allEven && sum >= 12;
                },
                () -> new TitaniumPlate(), true);
    }

    public static Quest steadfastHelm() {
        return new Quest(
                "Steadfast Helm",
                "ลดระยะเวลาของสถานะผิดปกติ",
                """
                        • เลือก 2 ช่อง
                        • ผลรวม = 10
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 2)
                        return false;

                    int sum = numbers.stream().mapToInt(i -> i).sum();
                    return sum == 10;
                },
                () -> new SteadfastHelm(), true);
    }

    // =========================
    // 💖 ฟื้นฟู
    // =========================

    public static Quest spiritFruit() {
        return new Quest(
                "Spirit Fruit",
                "ฟื้นฟู HP 10% ต่อเทิร์น เป็นเวลา 3 เทิร์น",
                """
                        • เลือก 3 ช่อง
                        • ผลรวม ≤ 9
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    int sum = numbers.stream().mapToInt(i -> i).sum();
                    return sum <= 9;
                },
                () -> new SpiritFruit(), true);
    }

    public static Quest grandPotion() {
        return new Quest(
                "Grand Potion",
                "ฟื้นฟู HP ทันที 50%",
                """
                        • เลือก 3 ช่อง
                        • ผลรวม = 12
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    int sum = numbers.stream().mapToInt(i -> i).sum();
                    return sum == 12;
                },
                () -> new GrandPotion(), true);
    }

    public static Quest phoenixOrb() {
        return new Quest(
                "Phoenix Orb",
                "ฟื้นคืนชีพ 1 ครั้ง ที่ 20% HP",
                """
                        • เลือกเลขเหมือนกัน 3 ช่อง
                        • ผลรวม ≥ 9
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    boolean same = numbers.get(0).equals(numbers.get(1)) &&
                            numbers.get(1).equals(numbers.get(2));

                    int sum = numbers.stream().mapToInt(i -> i).sum();

                    return same && sum >= 9;
                },
                () -> new PhoenixOrb(), true);
    }

    // =========================
    // 🔮 บัฟ
    // =========================

    public static Quest powerElixir() {
        return new Quest(
                "Power Elixir",
                "เพิ่มพลังโจมตี 30%",
                """
                        • เลือก 3 ช่อง
                        • ผลรวมระหว่าง 13–15
                        • ห้ามมีเลขซ้ำ
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    int sum = numbers.stream().mapToInt(i -> i).sum();
                    boolean noDup = numbers.stream().distinct().count() == 3;

                    return sum >= 13 && sum <= 15 && noDup;
                },
                () -> new PowerElixir(), true);
    }

    public static Quest timeSand() {
        return new Quest(
                "Time Sand",
                "มีโอกาสได้เทิร์นเพิ่ม 30%",
                """
                        • เลือกเลขจำนวนเฉพาะ (2,3,5,7)
                        • ผลรวม ≥ 11
                        """,
                (numbers, player) -> {

                    if (numbers.size() != 3)
                        return false;

                    List<Integer> primes = List.of(2, 3, 5, 7);

                    boolean allPrime = numbers.stream().allMatch(primes::contains);

                    int sum = numbers.stream().mapToInt(i -> i).sum();

                    return allPrime && sum >= 11;
                },
                () -> new TimeSand(), true);
    }

    public static Quest warHorn() {
        return new Quest(
                "War Horn",
                "เพิ่มพลังโจมตีและป้องกัน 15%",
                """
                        • เลือก 3 ช่อง
                        • ต้องมีเลข ≥ 6 อย่างน้อย 2 ช่อง
                        """,
                (numbers, player) -> {
                    if (numbers.size() != 3)
                        return false;
                    long count = numbers.stream().filter(n -> n >= 6).count();

                    return count >= 2;
                },
                () -> new WarHorn(), true);
    }
}