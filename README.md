# archiver
simple archiver
Написать библиотеку архивной системы
	Данная библиотека должна выполнять функционал архивной системы, основными задачами которой являются:
•	сохранение отдельных файлов или всего содержимого указанной директории в виде Zip-архива по указанному пути, ниже приведены доступные опции при формировании архива:
o	формирование архива с указанием пароля
o	формирование архива без указания пароля
o	формирование архива с подсчетом контрольной суммы SHA-1
o	формирование архива без подсчета контрольной суммы SHA-1

•	восстановление архивов из директории хранения в указанную директорию с распаковкой, ниже приведены доступные опции для восстановления архива:
o	восстановление и распаковка архива с указанием пароля
o	восстановление архива без указания пароля
o	восстановление архива с проверкой контрольной суммы SHA-1
o	восстановление архива без проверки контрольной суммы SHA-1

•	дополнительный функционал:
o	подсчет контрольной суммы уже хранящегося архива
o	переупаковка уже хранящегося архива без пароля с паролем

•	меню справки:
o	при запуске программа предлагает вывести список доступных команд при помощи команды помощи
 
Функциональные требования к архивной системе:
•	Сохранение
	Выполняется командой save с указанием атрибутов:
o	-f - атрибут указания исходного источника (файла или целой папки)
o	-t - атрибут указания целевой директории
o	-p - атрибут указания пароля 
o	-s - атрибут указания подсчета контрольной суммы
•	Восстановление 
Выполняется командой retrieve с указанием атрибутов:
o	-f - атрибут указания исходного архива
o	-t - атрибут указания целевой директории восстановления
o	-p - атрибут указания пароля 
o	-s - атрибут указания проверки контрольной суммы
•	Подсчет контрольной суммы
	Выполняется командой csum с указанием атрибутов
o	-f - атрибут указания исходного архива
•	Переупаковка уже хранящегося архива с паролем
	Выполняется командой repas
o	-f - атрибут указания исходного архива
o	-p - атрибут указания пароля 

	Требование к названию формируемых архивов:
	dd_mm_yyyy_hh_MM_ss.zip
	Требование к названию файла, содержащего контрольную сумму
	dd_mm_yyyy_hh_MM_ss.txt - однозначной соответствие с названием архива

	В случае ввода некорректных команд выводить мануал по текущей команде. Если вводят неизвестную команду - выводить общий мануал.

	Обрабатывать все нештатные ситуации, на каждый метод написать JUnit-тест.