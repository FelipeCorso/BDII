package br.furb.jsondb.core;

import br.furb.jsondb.core.command.CreateDatabaseCommand;
import br.furb.jsondb.core.command.CreateTableCommand;
import br.furb.jsondb.core.command.DropTableCommand;
import br.furb.jsondb.core.command.SetDatabaseCommand;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DropStatement;
import br.furb.jsondb.parser.SetDatabaseStatement;
import br.furb.jsondb.parser.TableIdentifier;

public class JsonDB {

	private static JsonDB intance = new JsonDB();

	private String currentDatabase;

	private JsonDB() {
	}

	public static JsonDB getInstance() {
		return intance;
	}

	public String getCurrentDatabase() {
		return currentDatabase;
	}

	public void setCurrentDatabase(String currentDatabase) {
		this.currentDatabase = currentDatabase;
	}

	public IResult executeSQL(String sql) {
		// TODO

		// 04. JsonDb submete código para o SqlParser
		// 05. SqlParser faz o reconhecimento preliminar das sentenças SQL
		// (texto até encontrar ";") e, para cada um, cria um objeto de
		// RawStatement
		// 06. SqlParser cria e retorna uma fila das sentenças SQL (sem o
		// reconhecimento dos tokens)
		// 07. JsonDb remove a primeira sentença da fila
		// 08. JsonDb solicita interpretação da sentença SQL para o SqlParser
		// 09. SqlParser reconhece a sentença, cria o objeto de IStatement
		// correspondente e retorna para o JsonDb

		/* === Os passos abaixo são executados pelas classes ICommand === */
		// 10. JsonDb verifica no metadados da base se a tabela envolvida no
		// comando existe
		// 11. JsonDb efetua as validações do comando
		// 12. JsonDb submete alterações para o Store
		// 13. Store executa e retorna o resultado das alterações

		// TODO if para saber qual comando executar

		// 14. JsonDb cria e armazena na sessão um objeto de resultados com o
		// comando executado, tempo decorrido desde o passo 07 e tempo absoluto
		// do SO
		// 15. Se houver mais um comando, pula para o passo 07
		// 16. JsonDb retorna os objetos de resultados para a UI
		// 17. Interface define a melhor forma de exibir os resultados

		return null;
	}

	private IResult createDatabase(CreateStatement statement) {
		return new CreateDatabaseCommand(statement).execute();
	}

	private IResult setDatabase(SetDatabaseStatement statement) {
		return new SetDatabaseCommand(statement).execute();
	}

	private IResult createTable(CreateStatement statement) {
		return new CreateTableCommand(statement).execute();
	}

	private IResult dropTable(DropStatement<TableIdentifier> statement) {
		return new DropTableCommand(statement).execute();
	}

}
