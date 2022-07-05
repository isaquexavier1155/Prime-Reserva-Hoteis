# Projeto Final -Prime Reserva Hoteis

Como desafio para o projeto final do CWI RESET, vamos criar a API de um aplicativo de aluguel de quartos e casas. O nome do aplicativo deverá ser definido por cada participante.

## Estrutura de Classes do Domínio

A estrutura de classes abaixo, representa o que é esperado que seja persistido pela aplicação. Nem sempre as interfaces REST vão representar exatamente a estrutura de classes do domínio. Os tipos de dados foram suprimidos, você deve definir o tipo mais adequado para cada dado. Somente os atributos que referenciam outras classes foram explicitados.

- Endereco
    - id;
    - cep;
    - logradouro;
    - numero;
    - complemento;
    - bairro;
    - cidade;
    - estado;
- Usuario
    - id;
    - nome;
    - email;
    - senha;
    - cpf;
    - dataNascimento;
    - Endereco endereco;
- CaracteristicaImovel
    - id;
    - descricao;
- Imovel
    - id;
    - identificacao;
    - TipoImovel tipoImovel
        - Apartamento
        - Casa
        - Hotel
        - Pousada
    - Endereco endereco;
    - Usuario proprietario;
    - List \<CaracteristicaImovel\> caracteristicas;
- FormaPagamento
    - Cartão de Crédito
    - Cartão de Débito
    - Pix
    - Dinheiro
- Anuncio
    - id;
    - TipoAnuncio tipoAnuncio;
        - Completo
        - Quarto
    - Imovel imovel;
    - Usuario anunciante;
    - valorDiaria;
    - List\<FormaPagamento\> formasAceitas;
    - descricao;
- Periodo
    - dataHoraInicial;
    - dataHoraFinal;
- Reserva
    - id;
    - Usuario solicitante;
    - Anuncio anuncio;
    - Periodo periodo;
    - quantidadePessoas;
    - dataHoraReserva;
    - Pagamento pagamento;
- Pagamento
    - valorTotal;
    - FormaPagamento formaEscolhida;
    - StatusPagamento statusPagamento;
        - Pendente
        - Pago
        - Estornado
        - Cancelado
    
# Funcionalidades

## 1. Usuário

### 1.1. Cadastro de Usuário
  - Assinatura
    - `POST /usuarios`
  - Parâmetros de Entrada: 
    - Usuario
        - nome*
        - email*
        - senha*
        - cpf*
        - dataNascimento*
        - Endereco endereco
            - cep*
            - logradouro*
            - numero*
            - complemento
            - bairro*
            - cidade*
            - estado*   
  - Saída esperada em caso de sucesso:
    - Status: `201 CREATED`
    - Body: 
        - Objeto `Usuario` contendo todos os campos cadastrados exceto o campo senha
  - Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - O campo CPF deve ser representado como uma String
    - Não deve ser possível cadastrar mais de um usuário com o mesmo E-Mail
        - Caso já exista outro usuário com o e-mail informado, deve lançar uma exceção que retorne o status 400 e uma mensagem informando o problema
        - Mensagem: `Já existe um recurso do tipo Usuario com E-Mail com o valor '{substituir-por-email-informado}'.`
          - Ex: `Já existe um recurso do tipo Usuario com E-Mail com o valor 'teste1@teste.com'.`
    - Não deve ser possível cadastrar mais de um usuário com o mesmo CPF
        - Caso já exista outro usuário com o CPF informado, deve lançar uma exceção que retorne o status 400 e uma mensagem informando o problema
        - Mensagem: `Já existe um recurso do tipo Usuario com CPF com o valor '{substituir-por-cpf-informado}'.`
          - Ex: `Já existe um recurso do tipo Usuario com CPF com o valor '12345678900'.`
    - O campo CEP deve aceitar somente o formato 99999-999. A validação deve ser realizada usando Bean Validations.
      - Caso seja informado um CEP com outro formato, deve ser retornado um erro com a mensagem: `O CEP deve ser informado no formato 99999-999.`
    - O campo CPF deve aceitar somente o formato 99999999999. A validação deve ser realizada usando Bean Validations.
      - Caso seja informado um CPF com outro formato, deve ser retornado um erro com a mensagem: `O CPF deve ser informado no formato 99999999999.`
    - Os campos Cidade e Estado serão representados como String
    - Caso seja informado um endereço, então os campos marcados com * são obrigatórios. A validação deve ser realizada usando Bean Validations.

### 1.2. Listar usuários
  - Assinatura
    - `GET /usuarios`
  - Parâmetros de Entrada
    - N/A    
  - Saída esperada em caso de sucesso
    - Status: `200 SUCCESS`
    - Body
        - List\<Usuario>
            - Usuario
                - id
                - nome
                - email
                - cpf
                - dataNascimento
                - Endereco endereco
                    - id
                    - cep
                    - logradouro
                    - numero
                    - complemento
                    - bairro
                    - cidade
                    - estado
  - Regras
    - O campo senha não deve ser retornado, porém o objeto a ser retornado é o `Usuario`
  - Desafio
    - Listar os usuários com paginação, em ordem alfabética pelo nome por padrão    

### 1.3. Buscar um usuário por id
  - Assinatura
    - `GET /usuarios/{idUsuario}`
  - Parâmetros de Entrada: 
    - idUsuario (path parameter)      
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: 
        - Usuario
            - id
            - nome
            - email
            - cpf
            - dataNascimento
            - Endereco endereco
                - id
                - cep
                - logradouro
                - numero
                - complemento
                - bairro
                - cidade
                - estado    
  - Comportamentos:
    - A aplicação deve obter o usuário através do id informado.
        - Caso nenhum usuário seja localizado, deve lançar uma exceção que retorne o status 404
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
  - Regras
    - O campo senha não deve ser retornado, porém o objeto a ser retornado é o `Usuario`

### 1.4. Buscar um usuário por cpf
  - Assinatura
    - `GET /usuarios/cpf/{cpf}`
  - Parâmetros de Entrada: 
    - cpf (path parameter)      
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body:
        - Usuario
            - id
            - nome
            - email
            - cpf
            - dataNascimento
            - Endereco endereco
                - id
                - cep
                - logradouro
                - numero
                - complemento
                - bairro
                - cidade
                - estado     
  - Comportamentos:
    - A aplicação deve obter o usuário através do CPF informado.
        - Caso nenhum usuário seja localizado, deve lançar uma exceção que retorne o status 404
        - Mensagem: `Nenhum(a) Usuario com CPF com o valor '{SUBSTITUIR-PELO-CPF-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Usuario com CPF com o valor '01245487848' foi encontrado.`
  - Regras
    - O campo senha não deve ser retornado, porém o objeto a ser retornado é o `Usuario`

### 1.5. Alterar um usuário
  - Assinatura
    - `PUT /usuarios/{id}`
  - Parâmetros de Entrada: 
    - id (path parameter)
    - AtualizarUsuarioRequest
        - nome*
        - email*
        - senha*
        - dataNascimento*
        - Endereco endereco
            - cep*
            - logradouro*
            - numero*
            - complemento
            - bairro*
            - cidade*
            - estado*
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: 
        - Objeto `Usuario` contendo todos os campos exceto o campo senha
  - Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - Não deve ser possível alterar o CPF de um usuário já cadastrado
    - A aplicação deve obter o usuario através do Id informado.
        - Caso nenhum usuario seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
    - Não deve ser possível cadastrar mais de um usuário com o mesmo E-Mail
        - Caso já exista outro usuário com o e-mail informado, deve lançar uma exceção que retorne o status 400 e uma mensagem informando o problema
        - Mensagem: `Já existe um recurso do tipo Usuario com E-Mail com o valor '{substituir-por-email-informado}'.`
          - Ex: `Já existe um recurso do tipo Usuario com E-Mail com o valor 'teste1@teste.com'.`
    - O campo CEP deve aceitar somente o formato 99999-999. A validação deve ser realizada usando Bean Validations.
      - Caso seja informado um CEP com outro formato, deve ser retornado um erro com a mensagem: `O CEP deve ser informado no formato 99999-999.`
    - Os campos Cidade e Estado serão representados como String
    - O campo senha não deve ser retornado

## 2. Imóvel

### 2.1. Cadastro de Imóvel
  - Assinatura
    - `POST /imoveis`
  - Parâmetros de Entrada: 
    - CadastrarImovelRequest
        - TipoImovel tipoImovel*
        - Endereco endereco*
            - cep*
            - logradouro*
            - numero*
            - complemento
            - bairro*
            - cidade*
            - estado*   
        - identificacao*
        - idProprietario*
        - List \<CaracteristicaImovel\> caracteristicas
            - CaracteristicaImovel
                - descricao 
  - Saída esperada em caso de sucesso:
    - Status: `201 CREATED`
    - Body: Objeto Imovel contendo todos os campos
  - Comportamentos:
    - A aplicação deve obter o usuario através do Id informado, para poder vincular ao Imovel antes de persistir.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
  - Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - O campo "identificacao" serve pra que o proprietário identifique o imóvel textualmente. Algo como "Casa da praia".
    - O campo CEP deve aceitar somente o formato 99999-999. A validação deve ser realizada usando Bean Validations.
      - Caso seja informado um CEP com outro formato, deve ser retornado um erro com a mensagem: `O CEP deve ser informado no formato 99999-999.`
    - Os campos Cidade e Estado serão representados como String

### 2.2. Listar imóveis
  - Assinatura
    - `GET /imoveis`
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: 
        - Lista de Imóveis
            - É esperado que sejam exibidos todos os atributos do Imóvel, bem como os atributos de Endereço, proprietário e características.
  - Desafio
    - Listar os imóveis com paginação, em ordem alfabética pelo campo identificacao por padrão

### 2.3. Listar imóveis de um proprietário específico
  - Assinatura
    - `GET /imoveis/proprietarios/{idProprietario}`
  - Parâmetros de Entrada: 
    - idProprietario (path parameter)      
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body:
        - Lista de Imóveis
            - É esperado que sejam exibidos todos os atributos do Imóvel, bem como os atributos de Endereço, proprietário e características.    
  - Comportamentos:
    - O sistema deve retornar somente os imóveis do proprietário informado
  - Desafio
    - Listar os imóveis com paginação, em ordem alfabética pelo campo identificacao por padrão

### 2.4. Buscar um imóvel por id
  - Assinatura
    - `GET /imoveis/{idImovel}`
  - Parâmetros de Entrada: 
    - idImovel (path parameter)      
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: Objeto Imóvel
        - É esperado que sejam exibidos todos os atributos do Imóvel, bem como os atributos de Endereço, proprietário e características.    
  - Comportamentos:
    - A aplicação deve obter o Imóvel através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Imovel com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Imovel com Id com o valor '999' foi encontrado.`

### 2.5. Excluir um imóvel
  - Assinatura
    - `DELETE /imoveis/{idImovel}`
  - Parâmetros de Entrada: 
    - idImovel (path parameter)      
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
  - Comportamentos:
    - A aplicação deve excluir o Imóvel através do Id informado.
      - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
      - Mensagem: `Nenhum(a) Imovel com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
        - Ex: `Nenhum(a) Imovel com Id com o valor '999' foi encontrado.`
  - Não deve ser possível excluir um imóvel que possua algum anúncio
    - Caso o imóvel possuia anúncios, o sistema deve lançar uma exceção com o status 400 e uma mensagem informando o problema
      - Mensagem: `Não é possível excluir um imóvel que possua um anúncio.`

## 3. Anúncio

### 3.1 Anunciar imóvel
  - Assinatura
    - `POST /anuncios`
  - Parâmetros de Entrada: 
    - CadastrarAnuncioRequest
      - idImovel*
      - idAnunciante*
      - TipoAnuncio tipoAnuncio*
      - valorDiaria*
      - formasAceitas*
      - descricao*
  - Saída esperada em caso de sucesso:
    - Status: `201 CREATED`
    - Body: Anuncio
  - Comportamentos:
    - A aplicação deve obter o Imóvel através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema    
        - Mensagem: `Nenhum(a) Imovel com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Imovel com Id com o valor '999' foi encontrado.`
    - A aplicação deve obter o anunciante (Usuario) através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema    
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
  - Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - Não deve ser possível criar mais de um anúncio para o mesmo imóvel
      - Caso já exista algum anúncio para o mesmo imóvel, a aplicação deve lançar uma exceção que retorne o código 400 e uma mensagem.
        - Mensagem: `Já existe um recurso do tipo Anuncio com IdImovel com o valor '{substituir-pelo-idimovel-informado}'.`
          - Ex: `Já existe um recurso do tipo Anuncio com IdImovel com o valor '12'.`

### 3.2. Listar anúncios
  - Assinatura
    - `GET /anuncios`
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: Lista de Anuncio
        - É esperado que sejam exibidos todos os atributos do Anuncio, bem como os atributos de Imóvel e anunciante.
  - Comportamentos:
    - Deve listar todos os anúncios armazenados no banco de dados
  - Desafio
    - Listar os anúncios com paginação, em ordem pelo valor da diária (valores menores primeiro)

### 3.3. Listar anúncios de um anunciante específico
  - Assinatura
    - `GET /anuncios/anunciantes/{idAnunciante}`
  - Parâmetros de Entrada: 
    - idAnunciante (path parameter)    
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: Lista de Anuncio
        - É esperado que sejam exibidos todos os atributos do Anuncio, bem como os atributos de Imóvel e anunciante.
  - Comportamentos:
    - O sistema deve retornar somente os anúncios que tenham sido realizados pelo anunciante informado
  - Desafio
    - Listar os anúncios com paginação, em ordem pelo valor da diária (valores menores primeiro)

### 3.4. Excluir um anúncio
  - Assinatura
    - `DELETE /anuncios/{idAnuncio}`
  - Parâmetros de Entrada: 
    - idAnuncio (path parameter)      
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
  - Comportamentos:
    - A aplicação deve obter o Anúncio através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Anuncio com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Anuncio com Id com o valor '999' foi encontrado.`
    - O anúncio não deve ser removido do banco de dados. Ao invés disso, deve ser feita uma exclusão lógica do registro. Faça as adaptações necessárias para que o registro permaneça na base mas não seja mais considerado em nenhuma outra busca. Dica: Possivelmente será necessário refatorar alguns métodos criados anteriormente.

## 4. Reserva

### 4.1. Realizar uma reserva
  - Assinatura
    - `POST /reservas`
  - Parâmetros de Entrada: 
    - CadastrarReservaRequest
      - idSolicitante*
      - idAnuncio*
      - Periodo periodo*
      - quantidadePessoas*
  - Saída esperada em caso de sucesso:
    - Status: `201 CREATED`
    - Body: 
      - InformacaoReservaResponse
        - idReserva
        - DadosSolicitanteResponse solicitante
          - id
          - nome
        - quantidadePessoas
        - DadosAnuncioResponse anuncio
          - id
          - Imovel
          - Usuario anunciante
          - List\<FormaPagamento\> formasAceitas
          - descricao
        - Periodo periodo (e todos os seus atributos)
        - Pagamento pagamento (e todos os seus atributos)
  - Comportamentos:
    - O formato da data esperado no período é: `yyyy-MM-dd HH:mm:ss`
    - Deve ser definida e registrada a Data/Hora do momento em que a reserva foi realizada
    - A aplicação aceita somente reservas iniciando as 14:00 e finalizando as 12:00. Caso seja informado um horário diferente, a aplicação de sobrescrever a informação e considerar estes horários arbitrariamente.    
    - Deve ser calculado o valor total para o pagamento, baseado no valor da diária e na quantidade de diárias solicitadas da reserva
    - A aplicação deve obter o usuario solicitante através do Id informado, para poder vincular à reserva antes de persistir.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
    - A aplicação deve obter o anuncio através do Id informado, para poder vincular à reserva antes de persistir.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Anuncio com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Anuncio com Id com o valor '999' foi encontrado.`
  - Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - Não deve ser possível reservar um imóvel com um período cuja data final seja menor que a data inicial
      - Caso ocorra essa situação, a aplicação deve lançar uma exceção com o status 400 com uma mensagem informando o problema
        - Mensagem: `Período inválido! A data final da reserva precisa ser maior do que a data inicial.`
    - Não deve ser possível reservar um imóvel com um período cuja diferença entre a data final e inicial seja menor que 1 dia
      - Caso ocorra essa situação, a aplicação deve lançar uma exceção com o status 400 com uma mensagem informando o problema
        - Mensagem: `Período inválido! O número mínimo de diárias precisa ser maior ou igual à 1.`
    - Não deve ser possível reservar um imóvel cujo solicitante seja o mesmo anunciante
      - Caso ocorra essa situação, a aplicação deve lançar uma exceção com o status 400 com uma mensagem informando o problema
        - Mensagem: `O solicitante de uma reserva não pode ser o próprio anunciante.`
    - Não deve ser possível reservar um imóvel que já possua uma reserva ativa no mesmo período. 
        - Observe que o período pode possuir sobreposição em apenas uma data. Mesmo assim não deve ser possível realizar a reserva. 
        - O conceito de reserva "ativa" consiste em uma reserva que não tenha sido estornada nem cancelada.
        - Caso ocorra essa situação, a aplicação deve lançar uma exceção com o status 400 com uma mensagem informando o problema
          - Mensagem: `Este anuncio já esta reservado para o período informado.`
    - Caso a reserva seja de um Hotel, o número mínimo de pessoas é 2
        - Caso seja informado um número inferior, deve lançar uma exceção com o status 400 com uma mensagem informando o problema    
          - Mensagem: `Não é possivel realizar uma reserva com menos de {NUMERO-MINIMO-PESSOAS} pessoas para imóveis do tipo {TIPO-INFORMADO}`
            - Ex: `Não é possivel realizar uma reserva com menos de 2 pessoas para imóveis do tipo Hotel`
    - Caso a reserva seja de uma Pousada, o número mínimo de diárias é 5
        - Caso seja informado um período inferior, deve lançar uma exceção com o status 400 com uma mensagem informando o problema   
          - Mensagem: `Não é possivel realizar uma reserva com menos de {NUMERO-MINIMO-DIARIAS} diárias para imóveis do tipo {TIPO-INFORMADO}`
            - Ex: `Não é possivel realizar uma reserva com menos de 5 diárias para imóveis do tipo Pousada`

### 4.2. Listar reservas de um solicitante específico
  - Assinatura
    - `GET /reservas/solicitantes/{idSolicitante}`
  - Parâmetros de Entrada: 
    - idSolicitante (path parameter)     
    - Periodo periodo (opcional)
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: List de Reserva
      - É esperado que sejam exibidos todos os atributos da Reserva, bem como os atributos de Anuncio e Soliciante.
  - Comportamentos:
    - O sistema deve retornar somente as reservas do solicitante informado    
    - Caso nenhum período seja informado, o sistema deve retornar todas as reservas. Considere sempre o período completo, se for informada apenas uma das duas datas, o sistema vai considerar que nenhuma data foi informada.
    - Caso seja informado um período, o sistema deve retornar somente as reservas cujas datas esteja dentro do período informado.
  - Desafio
    - Listar as reservas com paginação, ordenando pela data do fim da reserva(Datas maiores primeiro).

### 4.3. Listar reservas de um anunciante específico
  - Assinatura
    - `GET /reservas/anuncios/anunciantes/{idAnunciante}`
  - Parâmetros de Entrada: 
    - idAnunciante (path parameter)    
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: List de Reserva
      - É esperado que sejam exibidos todos os atributos da Reserva, bem como os atributos de Anuncio e Soliciante.
  - Comportamentos:
    - O sistema deve retornar somente as reservas do anunciante informado    
  - Desafio
    - Listar as reservas com paginação, ordenando pela data do fim da reserva(Datas maiores primeiro).

### 4.4. Pagar reserva
  - Assinatura
    - `PUT /reservas/{idReserva}/pagamentos`
  - Parâmetros de Entrada: 
    - idReserva (path parameter)
    - formaPagamento
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
  - Comportamentos:
    - A aplicação deve obter a Reserva através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema    
        - Mensagem: Nenhum(a) Reserva com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.
          - Ex: Nenhum(a) Reserva com Id com o valor '999' foi encontrado.        
    - Alterar o status do Pagamento da reserva para "Pago"
  - Regras
    - Não deve ser possível realizar um pagamento com uma forma de pagamento que não seja aceita pelo anúncio.
      - Caso isso aconteça, deve lançar uma exceção com o status 400 e uma mensagem informando o problema 
        - Mensagem: `O anúncio não aceita {FORMA-PAGAMENTO-INFORMADA} como forma de pagamento. As formas aceitas são {FORMAS-ACEITAS-PELO-ANUNCIO}.`
          - Ex: `O anúncio não aceita PIX como forma de pagamento. As formas aceitas são DINHEIRO, CARTAO_CREDITO.`
    - Não deve ser possível realizar o pagamento de uma reserva paga, estornada ou cancelada
      - Caso esteja em algum desses status, deve lançar uma exceção com o status 400 e uma mensagem informando o problema 
        - Mensagem: `Não é possível realizar o pagamento para esta reserva, pois ela não está no status PENDENTE.`

### 4.5. Cancelar uma reserva
  - Assinatura
    - `PUT /reservas/{idReserva}/pagamentos/cancelar`
  - Parâmetros de Entrada: 
    - idReserva (path parameter)
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
  - Comportamentos:
    - A aplicação deve obter a Reserva através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema    
        - Mensagem: `Nenhum(a) Reserva com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Reserva com Id com o valor '999' foi encontrado.`
    - Alterar o status do Pagamento da reserva para "Cancelado"
  - Regras
    - Não deve ser possível realizar o cancelamento de uma reserva paga, estornada, cancelada
      - Caso esteja em algum desses status, deve lançar uma exceção com o status 400 e uma mensagem informando o problema    
        - Mensagem: `Não é possível realizar o cancelamento para esta reserva, pois ela não está no status PENDENTE.`

### 4.6. Estornar reserva
  - Assinatura
    - `PUT /reservas/{idReserva}/pagamentos/estornar`
  - Parâmetros de Entrada: 
    - idReserva (path parameter)
  - Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
  - Comportamentos:
      - A aplicação deve obter a Reserva através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema    
        - Mensagem: `Nenhum(a) Reserva com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
          - Ex: `Nenhum(a) Reserva com Id com o valor '999' foi encontrado.`
    - Alterar o status do Pagamento da reserva para "Estornado"
    - A forma de pagamento escolhida deve ser removida
  - Regras
    - Não deve ser possível estornar o pagamento de uma reserva pendente, estornada, cancelada
      - Caso esteja em algum desses status, deve lançar uma exceção com o status 400 e uma mensagem informando o problema    
        - Mensagem: `Não é possível realizar o estorno para esta reserva, pois ela não está no status PAGO.`

## 5. Desafio Extra

O desafio consiste em realizar alguma implementação que não foi mostrada em aula e que não daremos muitas dicas.

### 5.1 Obter imagem de avatar para usuários

Será necessário adaptar a entidade de usuário para que ela passe a ter um novo atributo que represente a imagem de avatar dentro do sistema. Este atributo vai guardar a URL de uma imagem.

Este atributo não será recebido pelo objeto de request. A aplicação deverá realizar a chamada à uma API externa que retorna essa URL e então vincular ao usuário que estiver sendo criado.

API que será usada: 
- GET https://some-random-api.ml/img/dog
- Retorno:
  ```json
  {
      "link": "https://i.some-random-api.ml/kC1VFB2J2F.jpg"
  }
  ```

Sugestão de biblioteca para implementar essa integração: Feign Client

Referências: 
- https://cloud.spring.io/spring-cloud-openfeign/reference/html/
- https://www.baeldung.com/spring-cloud-openfeign

### 5.2 Alterar a operação de excluir imóvel para que seja feita a exclusão lógica

Este desafio consiste em modificar o funcionamento da exclusão de imóveis para que funcione através do mecanismo de exclusão lógica, o mesmo adotado no item 3.4.

## Observações
  - Será aceitável usar os objetos de entidade como parâmetro de entrada quando o único campo que não será usado é o `id`. Ex: Cadastro de Usuários.

---

### Collection Postman 

Criamos uma collection contendo exemplos de requisição e de respostas. Use como apoio para consultar como as urls são esperadas, bem como os payloads de request e response: Copie a URL https://www.getpostman.com/collections/ea743a6f9b05cf404b3e e faça o import no Postman.  

### Entrega

- O código deve estar comitado no repositório até as **23:59:59** do dia **21/11/2021** (Domingo). Qualquer commit feito após este horário, será desconsiderado na avaliação.

### Dicas

- Organize o seu desenvolvimento. São diversas etapas, crie pequenas entregas e comemore cada uma delas. Sugestões de ferramentas para organizar:
  - [Trello](https://trello.com/)
  - [Notion](https://www.notion.so/pt-br)
  - Bloco de Notas

- Priorize as funcionalidades completas, evite começar tudo sem finalizar. Vamos executar testes automatizados em cada funcionalidade, então tem mais valor 2 ou 3 funcionalidades completas do que todas sem funcionar.

- Observe atentamente o texto de todas as mensagens. Elas serão validadas por nós e devem obedecer exatamente o que foi especificado.

- Para validações simples existe um padrão chamado BeanValidations. Ele já está configurado no projeto.
    - As bean validations são um conjunto de Annotations que permitem definir regras para cada atributo de uma classe.
    - É possível validar tamanho, formato, existência ou não de valores, tamanho de listas, etc.
    - Lista das Bean Validations: https://beanvalidation.org/2.0/spec/#builtinconstraints
    - **IMPORTANTE!** Não esqueça de usar o `@Valid` no parâmetro da Controller e nas referências de objetos complexos!
    - Lembre-se de que as anotações são necessárias somente nos objetos usados como parâmetro de entrada em métodos da Controller. 

- Atualmente todas as classes de domínio que criamos, acabávamos criando gets e sets para a maioria dos atributos. Também criávamos construtores. Existe um plugin que facilita a nossa vida nesse sentido, o Lombok.
    - Com o Lombok, basta adicionar a Annotation `@Getter` e `@Setter` na classe, para que automaticamente sejam criados os métodos getters e setters para todos os atributos. Eles não ficam visíveis no código, mas estarão disponiveis ao usar uma instância dessa classe.
    - IntelliJ já possui um plugin nativo, mas caso não esteja disponível na sua versão, basta acessar o menu `File > Settings > Plugins`, acessar a guia Marketplace e buscar por Lombok.
      - O próximo passo é habilitar a opção `Enable Annotation processor` no menu `File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors`
    - Lombok: https://projectlombok.org/setup/maven
    - **IMPORTANTE!** Este plugin é opcional, mas é um facilitador. Se achar que está complicando, evite usar e faça da maneira tradicional, isso não vai mudar a nossa avaliação.

- Para definir o HTTP Status Code que será retornado quando uma determinada exceção for lançada, basta adicionar a Annotation no nível da classe `@ResponseStatus` que recebe por parâmetro o HttpStatus. Ex: `@ResponseStatus(HttpStatus.NOT_FOUND)`.

- Para definir o HTTP Status Code que será retornado por padrão em um método da controller, basta adicionar a Annotation no nível do método `@ResponseStatus`. que recebe por parâmetro o HttpStatus. Ex: `@ResponseStatus(HttpStatus.CREATED)`. Por padrão, o Spring vai sempre retornar o Status Code `200 SUCCESS` caso o método seja finalizado com sucesso. Portanto, a Annotation só precisa ser usada nas situações onde queremos que o retorno de sucesso não seja o `200`.

- A paginação funciona com valores padrões (através da anotação `@PageableDefault`). Caso nenhum parâmetro seja informado na url, serão assumidos os valores padrões do `@PageableDefault`. Para que seja possível o cliente navegar nas páginas, são necessários informar alguns parâmetros na url:
  - `page`: Permite definir em qual página estamos. Inicia em 0 (zero) e não pode ser negativo.
  - `size`: define a quantidade de itens por página. Precisa ser maior que zero.
  - `sort`: define o atributo que será usado para ordenar e o sentido da ordenação (crescente = `asc` e descrescente = `desc`) 
  - Exemplos: 
    - http://localhost:8080/usuarios - Vai utilizar as informações padrões do `@PageableDefault`
    - http://localhost:8080/usuarios?page=1&size=10&sort=nome,desc - Vai exibir até 10 itens da segunda página e ordenar pelo nome de Z para A
    - http://localhost:8080/usuarios?page=2&size=4&sort=nome,asc - Vai exibir até 4 itens da terceira página e ordenar pelo nome de A para Z
    - http://localhost:8080/usuarios?page=1&size=10&sort=nome,desc&sort=dataNascimento,desc - Vai exibir até 10 itens da segunda página e ordenar pelo nome de Z para A e depois ainda ordenar pela data de nascimento em ordem descrente
  - Não é necessário criar estes parâmetros na controller, o próprio `@PageableDefault` em conjunto com um parâmetro do tipo `Pageable` já faz esse papel.
  - O Swagger não apresenta corretamente os parâmetros da classe `Pageable`, então recomendamos incluir a anotação `@ApiIgnore` para que o Swagger apresente corretamente. Ex: `listar(@PageableDefault(sort = "nome") @ApiIgnore Pageable pageable)`
  - Referência: 
    - [5.2. Customizing the Paging Parameters](https://www.baeldung.com/spring-data-web-support#2-customizing-the-paging-parameters)
    - [Pagination and Sorting using Spring Data JPA](https://www.baeldung.com/spring-data-jpa-pagination-sorting)

- Para acessar o Swagger, utilize a URL: http://localhost:8080/swagger-ui/

- Para acessar o console do H2, utilize a URL: http://localhost:8080/h2 e, no campo `JDBC URL:` informe o valor `jdbc:h2:~/tcc-h2-db`.
 Deixe os campos de usuario e senha com os valores padrões. 
Sempre garanta que o campo `JDBC URL` esteja conforme a entrada `spring datasource url` do arquivo `application.yml`. 
