-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Мар 07 2021 г., 09:09
-- Версия сервера: 10.4.17-MariaDB
-- Версия PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `quizsystem`
--

-- --------------------------------------------------------

--
-- Структура таблицы `options_to_questions`
--

CREATE TABLE `options_to_questions` (
  `question` varchar(255) NOT NULL,
  `option_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `options_to_questions`
--

INSERT INTO `options_to_questions` (`question`, `option_name`) VALUES
('Which tool is better?', 'IntellijIdea'),
('Which tool is better?', 'Eclipse'),
('Do u like AstanaIT University?', 'Yes'),
('Do u like AstanaIT University?', 'No');

-- --------------------------------------------------------

--
-- Структура таблицы `people`
--

CREATE TABLE `people` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `people`
--

INSERT INTO `people` (`username`, `password`) VALUES
('admin', 'admin1!'),
('admin2', 'admin2!'),
('Ino', 'ino1!'),
('Naruto', 'naruto1!'),
('Sasuke', 'sasuke1!');

-- --------------------------------------------------------

--
-- Структура таблицы `questions`
--

CREATE TABLE `questions` (
  `question` varchar(255) NOT NULL,
  `correct_option` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `questions`
--

INSERT INTO `questions` (`question`, `correct_option`) VALUES
('Do u like AstanaIT University?', 'Yes'),
('Which tool is better?', 'IntellijIdea');

-- --------------------------------------------------------

--
-- Структура таблицы `questions_to_quizzes`
--

CREATE TABLE `questions_to_quizzes` (
  `quiz_id` int(11) NOT NULL,
  `question` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `questions_to_quizzes`
--

INSERT INTO `questions_to_quizzes` (`quiz_id`, `question`) VALUES
(1, 'Which tool is better?'),
(2, 'Do u like AstanaIT University?');

-- --------------------------------------------------------

--
-- Структура таблицы `quizzes`
--

CREATE TABLE `quizzes` (
  `id` int(11) NOT NULL,
  `topic` varchar(255) NOT NULL,
  `time` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `quizzes`
--

INSERT INTO `quizzes` (`id`, `topic`, `time`) VALUES
(1, 'OOPjava', 1),
(2, 'MidtermQuiz', 2);

-- --------------------------------------------------------

--
-- Структура таблицы `quizzes_to_students`
--

CREATE TABLE `quizzes_to_students` (
  `student_name` varchar(255) NOT NULL,
  `quiz_id` int(11) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `quizzes_to_students`
--

INSERT INTO `quizzes_to_students` (`student_name`, `quiz_id`, `score`) VALUES
('Naruto', 2, 10);

-- --------------------------------------------------------

--
-- Структура таблицы `students_to_teams`
--

CREATE TABLE `students_to_teams` (
  `team_name` varchar(255) NOT NULL,
  `student_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `students_to_teams`
--

INSERT INTO `students_to_teams` (`team_name`, `student_name`) VALUES
('Team7', 'Sasuke'),
('Team10', 'Ino'),
('Team7', 'Naruto');

-- --------------------------------------------------------

--
-- Структура таблицы `teams`
--

CREATE TABLE `teams` (
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `teams`
--

INSERT INTO `teams` (`name`) VALUES
('Team10'),
('Team7');

-- --------------------------------------------------------

--
-- Структура таблицы `teams_to_quizzes`
--

CREATE TABLE `teams_to_quizzes` (
  `quiz_id` int(11) NOT NULL,
  `team_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `teams_to_quizzes`
--

INSERT INTO `teams_to_quizzes` (`quiz_id`, `team_name`) VALUES
(1, 'Team7'),
(1, 'Team10'),
(0, 'Team7'),
(2, 'Team7');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `people`
--
ALTER TABLE `people`
  ADD PRIMARY KEY (`username`);

--
-- Индексы таблицы `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`question`);

--
-- Индексы таблицы `quizzes`
--
ALTER TABLE `quizzes`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `teams`
--
ALTER TABLE `teams`
  ADD PRIMARY KEY (`name`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `quizzes`
--
ALTER TABLE `quizzes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
