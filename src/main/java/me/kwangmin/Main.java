package me.kwangmin;

// 코드를 실행하려면 오른쪽 상단의 초록색 재생 버튼(Run)을 누르세요.
public class Main {
    // 메서드 시그니처를 public static void main(String[] args)로 수정했습니다.
    public static void main(String[] args) {

        // IO 대신 System.out을 사용합니다.
        System.out.println(String.format("Hello and welcome!"));

        for (int i = 1; i <= 5; i++) {
            // 여기도 System.out으로 변경합니다.
            System.out.println("i = " + i);
        }
    }
}