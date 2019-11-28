public class SimpleSearch {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Bob", "LuLu", "Linus");
        List<Intger> ages = Arrays.asList(11, 22, 55);

        List<Param> withFlatMap = names.stream()
                .flatMap(i -> ages.stream()
                .map(j -> {
                    Param param = Param.builder()
                            .name(i)
                            .age(j)
                            .build();
                }))
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