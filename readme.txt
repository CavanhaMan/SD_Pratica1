RPC
- IMPLEMENTAÇÃO DE RPC em Java, denomina-se RMI

Todos os gets e setters ficam dentro de uma classe
que estará em uma máquina remota.
Eu chamo os procedimentos (consumo) remotamente.

Eu irei precisar:
- parametros de entrada
- tipo de saída
- nome do serviço

SINTAXE e SEMANTICA
sintaxe é o nome do serviço, os parametros e os retornos

A função de um procedimento STUB é que vai desempacotar
os argumentos da mensagem de requisição, encaminhar pra
execução, pegar a resposta, empacotar e reenviar.

