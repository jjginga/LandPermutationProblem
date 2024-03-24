# LandPermutationProblem
This was a problem given in the course [Introduction to Artificial Intelligence](https://guiadoscursos.uab.pt/en/ucs/introducao-a-inteligencia-artificial/) at [Universidade Aberta](https://portal.uab.pt/?lang=en) in the 2022/2023 school year. 

The lands are square and have equal value. Neighboring lands can be exchanged. The owners aim to swap lands to reduce the number of borders. Any swap that increases the number of borders is not accepted. If there is a swap that results in a greater reduction of the number of borders, another swap with a lesser reduction is not accepted.

The problem can be described as follows: an instance is an NxM matrix of houses with K colors. The matrix represents the map, and the colors represent the owners, also identified by letters or numbers. Two houses of distinct colors sharing a side create a border. The goal is to reduce the number of borders. An action is a land swap, which consists of exchanging the colors of two continuous houses. This action is only valid if the number of borders does not increase as a result of the swap, and there is no other action with a greater reduction of the number of borders. A sequence of actions is sought that leads to a number of borders equal to or lower than a value W.

Example instance of a 3x3 houses of 3 colors. A solution with 6 or fewer borders is sought:
<img width="93" alt="image" src="https://github.com/jjginga/LandPermutationProblem/assets/60369139/928c6866-b041-4414-8a00-7fb5e95d1a0b">


The current state has 4 vertical borders and 4 horizontal borders, totaling 8 borders. Each border can originate an action of swapping the colors of the two houses, but this action is only valid if the number of borders does not increase as a result of the swap, and there is no other action with a greater reduction.

There are two marked border actions. The action in red causes an increase in the number of borders. House B had 2 borders, in the new position, it would have 3, and house C had 2 borders would go to 4. As the number of borders increases, the action is not valid.

The green action has house C with 2 borders and house B with another 2 borders. By swapping, house C in the new position ends up with 3 borders, while house B goes down to just one border. Thus, the number of borders remains at 4, making it a valid action, as there is no other action at this moment that reduces the number of borders.

Consider the following parameters for 7 instances:
ID | N | M | K | W1 | W2
---|---|---|---|----|---
1  | 3 | 3 | 3 | 6  | 5
2  | 2 | 4 | 2 | 4  | 2
3  | 4 | 4 | 3 | 10 | 9
4  | 4 | 5 | 2 | 10 | 9
5  | 4 | 10| 4 | 30 | 25
6  | 8 | 10| 2 | 41 | 35
7  | 8 | 10| 8 | 70 | 62

Each instance has two versions, version 1 (W1) and version 2 (W2). [Challenge: for students who have an operational implementation, and after the report is done, they can try to solve the last 3 instances with W of 22, 21, and 56 respectively, considering this a third version]

In addition to the parameters, the initial coloring of the houses is defined for each instance, each color identified with an integer, the letter A is associated with the number 1, and so on:
<img width="445" alt="image" src="https://github.com/jjginga/LandPermutationProblem/assets/60369139/d82fe75e-f0d5-4cca-86be-9e1fd7474988">


The instance maps can be defined as matrices with a maximum size of 10x10 and initialized statically in the code:
```java
{
    {{1,2,3},{1,2,2},{3,3,1}},
    {{1,2,2,2},{1,2,1,1}},
    {{1,2,2,2},{1,3,3,3},{1,2,1,1},{1,1,3,2}},
    {{1,1,2,1,1},{2,2,1,2,1},{1,1,2,1,2},{2,1,1,2,1}},
    {{1,2,2,2,2,1,2,2,2,2},{1,3,3,3,4,1,3,3,3,4},{1,2,1,4,3,1,2,1,4,3},{1,4,4,4,3,1,4,4,4,3}},
    {{1,1,2,1,1,1,1,2,1,1},{2,2,1,2,1,2,2,1,2,1},{1,1,2,1,2,1,1,2,1,2},{2,1,1,2,1,2,1,1,2,1},{1,1,2,1,1,1,1,2,1,1},{2,2,1,2,1,2,2,1,2,1},{1,1,2,1,2,1,1,2,1,2},{2,1,1,2,1,2,1,1,2,1}},
    {{1,1,2,8,8,1,4,3,1,4},{2,2,1,8,3,8,4,3,2,1},{1,1,8,8,3,1,6,2,1,4},{2,1,1,3,1,2,1,1,4,4},{1,7,7,3,1,1,5,6,4,4},{2,2,1,3,1,2,2,1,6,6},{1,7,2,7,5,5,5,5,1,6},{2,7,7,7,1,5,5,1,6,6}}
}
