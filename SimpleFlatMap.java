import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleFlatMap {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Bob", "LuLu", "Linus");
        List<Integer> ages = Arrays.asList(11, 22, 55);

        List<Param> withFlatMap = names.stream()
                .flatMap(i -> ages.stream()
                .map(j -> Param.builder()
                            .name(i)
                            .age(j)
                            .build()
                ))
                .collect(Collectors.toList());

        System.out.println(withFlatMap);

    }

    @Data
    @Builder
    class Param {

        private String name;

        private Integer age;

    }

}