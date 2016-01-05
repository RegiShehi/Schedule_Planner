<?php
/*
 * Simple JSON generation from static array
 */

$department_subjects = array(
    1 => array(
        "id" => 1,
        "department" => "COS",
        "courses" => array(
            array("id" => 1, "name" => "Software Development using C++", "code" => "COS 120a", "credits" => "Cr. 3", "description" => "The purpose of the course is to introduce the basic concepts of computer science using the C++ programming language. Extensive lab exercises emphasize the use of the programming language’s facilities for computer problem solving, applying a software development method based on top-down design, and the traditional concept of a software life cycle." ),
            array("id" => 2, "name" => "Discrete Structures", "code" => "COS 150a", "credits" => " Cr.3", "description" => "Presents fundamental discrete mathematics concepts which are essential to understanding the capabilities and limitations of computers. The concepts covered include propositional logic and Boolean algebra, sets, relations, functions, counting, graphs, and computability on abstract machines." ),
			array("id" => 3, "name" => "Software Engineering", "code" => "COS 315a", "credits" => " Cr.3", "description" => "Presents a broad view of software engineering with different software engineering techniques that can be applied to practical projects. Provides the knowledge and tools necessary for the specification, design. Topics include process models, human factors, software design and specification methodologies, programming techniques and tools, and validation principles."),
        )
    ),
    2 => array(
        "id" => 2,
        "department" => "INF",
        "courses" => array(
            array("id" => 1, "name" => "Visual Basic Programming", "code" => "INF 110a", "credits" => " Cr.3", "description" => "This course introduces the basic concepts of programming to students who have little or no programming experience using the Visual Basic programming language. The major constructs of “structured” programming will be covered, such as the sequential execution of program statements; the selective execution of program statements; and the repetitive execution of program statements"),
            array("id" => 2, "name" => "Data Base Systems", "code" => "INF 280a", "credits" => " Cr.3", "description" => "The course introduces the fundamental concepts of database theory and its applications. Topics include: foundations of databases, database design through entity-relationship model, relational database model, normalization; SQL etc. "),
			array("id" => 3, "name" => "Web Server Technologies", "code" => "INF 335a", "credits" => " Cr.3", "description" => "The course provides students with an understanding of the technologies that support Web server-based interactions and their impact on the World Wide Web. Focus is on the investigation of the most current technologies, such as PHP and ASP,NET."),
        )
    ),
    3 => array(
        "id" => 3,
        "department" => "BUS",
        "courses" => array(
            array("id" => 1, "name" => "Business Law", "code" => "BUS 200a", "credits" => " Cr.3", "description" => "Develops critical thinking through an analysis of unstructured legal problems. Emphasis is placed on learning and understanding commercial law in the sale of goods. Prerequisites: Sophomore standing"),
			array("id" => 2, "name" => "Business Ethics", "code" => "BUS 300a", "credits" => " Cr.4", "description" => "In a world dominated by business, questions constantly arise regarding the propriety of various business relations and practices. This course will focus on a number of these relationships etc. Gen. Ed: Moral and Philosophical Reasoning. (WIC) Prerequisites: declared BUS major, junior standing."),
			array("id" => 3, "name" => "Marketing", "code" => "BUS 260a", "credits" => " Cr.3", "description" => "Introduces students to major concepts and methods used in marketing goods, services, and other products and develops students’ ability to use their understanding in business situations. Prerequisites: ECO 101, ECO 102." ),
        )
    ),
    4 => array(
        "id" => 4,
        "department" => "ECO",
        "courses" => array(
            array("id" => 1, "name" => "Principles of Microeconomics", "code" => "ECO 101a", "credits" => " Cr.3", "description" => "Analysis of the structure and functioning of modern economic institutions, with special emphasis on the market. Analysis of economic decision-making by individuals, firms, and governments. Gen. Ed: social and cultural analysis."),
			array("id" => 2, "name" => "Econometrics", "code" => "ECO 310a", "credits" => " Cr.3", "description" => "The course studies the mathematical tools and statistical techniques of econometrics analysis and applies its methodology to economics in particular and social sciences in general.  (WIC) Prerequisites: STA 105, MAT 103."),
			array("id" => 3, "name" => "Money and Banking", "code" => "ECO 212a", "credits" => " Cr.4", "description" => "This course describes the role of financial institutions,financial markets in modern market economies. It includes descriptions and financial instruments, including money, asset pricing and interest rate determination, exchange rate mechanisms in theory and practice, financial market regulation and innovation, and monetary policy. Prerequisite: ECO 102." ),
        )
    ),
    5 => array(
        "id" => 5,
        "department" => "MAT",
        "courses" => array(
            array("id" => 1, "name" => "Calculus I", "code" => "MAT 103a", "credits" => " Cr.3", "description" => "The course develops the initial notions and skills of analysis in the real line like: limits and continuity; derivatives (the problem of “rates of change”) and curve sketching; integrals (the “area” or “accumulation” problem) and techniques of integration."),
			array("id" => 2, "name" => "Elementary Linear Algebra and Analytical Geometry", "code" => "MAT 105a", "credits" => " Cr.3", "description" => "The course offers a general view to some important ideas and techniques in the field. Starting with a discussion of systems of linear equations (the natural source of the subject) the important technique of matrices, matrix operations and determinants is considered."),
			array("id" => 3, "name" => "Introduction to Abstract Algebra", "code" => "MAT 205a", "credits" => " Cr.3", "description" => "The course offers an introduction to the basic algebraic structures, like groups, rings, integral domains and fields. Fundamental structure theorems for factorization are discussed. Prerequisite: MAT 105."),
        )
    )
);
?>
