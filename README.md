# API com exemplos de buscas dinâmicas #

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

### Filtros de campos
 
 - Realizado com **Criteria - JpaSpecificationExecutor**

Filtros são informados no padrão *inline* a partir do paramêtro *filter*, conforme exemplos abaixo:
 
 - eq ou == : operador de igualdade;
 - lt ou < : verifica se o valor de um determinado atributo é menor que um valor informado;
 - gt ou > : verifica se o valor de um determinado atributo é maior que um valor informado;
 - le ou <= : verifica se o valor de um determinado atributo é menor ou igual a um valor informado;
 - ge ou >= : verifica se o valor de um determinado atributo é maior ou igual a um valor informado;
 - in : verifica se o valor de um determinado atributo corresponde a um ou mais itens de uma lista de valores informada;
 - like : verifica se um trecho de texto está contido no valor de um determinado atributo. Deverá ser utilizado asterisco, a fim de especificar se o texto informado deve corresponder à parte inicial, final ou se deve estar contido no conteúdo pesquisado. Exemplos: pessoa.nome like Pedro (nome deve começar com Pedro) ou pessoa.nome like Pedro (nome deve terminar com Pedro) ou pessoa.nome_ like Pedro (nome deve conter o valor Pedro);
 - not : operador de negação. Deve ser utilizado em conjunto com os operadores in (not-in), like (not-like) e eq (ne ou !=).

#### Exemplo
 
**Criteria - JpaSpecificationExecutor**
- /api/criteria?filter=name not Helena,artur,Claudia;city in tubarão, São Paulo;id ge 2;mon like \*a* 
- - A consulta acima contempla as seguintes condições:
- - - **name NOT IN 'Helena','artur'**
- - - **city IN 'tubarão','São Paulo'**
- - - **id >= 2**
- - - **mon like '%a%'**