% Main module
-module(task_manager).

% Export start function
-export([start/0]).

% Start the program
start() ->
    io:format("~n=== TASK MANAGER ===~n"),
    menu([]).

% Show menu and handle options
menu(Tasks) ->
    io:format("~n1. Add Task~n"),
    io:format("2. View Tasks~n"),
    io:format("3. Complete Task~n"),
    io:format("4. Exit~n"),
    Choice = string:trim(io:get_line("Choose option: ")),

    case Choice of
        "1" ->
            menu(add_task(Tasks));
        "2" ->
            view_tasks(Tasks),
            menu(Tasks);
        "3" ->
            menu(complete_task(Tasks));
        "4" ->
            io:format("Goodbye!~n");
        _ ->
            io:format("Invalid option~n"),
            menu(Tasks)
    end.

% Add a new task
add_task(Tasks) ->
    Desc = string:trim(io:get_line("Enter task: ")),
    case Desc of
        "" ->
            io:format("Empty task not allowed~n"),
            Tasks;
        _ ->
            Num = length(Tasks) + 1,
            Tasks ++ [{Num, Desc, pending}]
    end.

% View all tasks
view_tasks([]) ->
    io:format("No tasks available~n");

view_tasks(Tasks) ->
    io:format("~nTasks:~n"),
    print_tasks(Tasks).

% Print tasks recursively
print_tasks([]) ->
    ok;

print_tasks([{N, D, S} | Rest]) ->
    io:format("~p. ~s [~p]~n", [N, D, S]),
    print_tasks(Rest).

% Complete a task
complete_task([]) ->
    io:format("No tasks to complete~n"),
    [];

complete_task(Tasks) ->
    view_tasks(Tasks),
    Input = string:trim(io:get_line("Task number: ")),

    case string:to_integer(Input) of
        {error, _} ->
            io:format("Invalid input~n"),
            Tasks;
        {Num, _} ->
            update_task(Num, Tasks)
    end.

% Update task status
update_task(_, []) ->
    [];

update_task(Num, [{N, D, S} | Rest]) ->
    if
        Num =:= N ->
            [{N, D, completed} | update_task(Num, Rest)];
        true ->
            [{N, D, S} | update_task(Num, Rest)]
    end.

    