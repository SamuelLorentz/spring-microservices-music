# spring-microservices-music

- Obedece as melhores práticas de montagem de APIs RESTful:

  1. Organização das APIs ao longo de recursos.
  2. APIs Padronizadas. 
  3. Evite APIs anêmicas. Evite projetar uma interface REST que espelhe ou dependa da estrutura interna dos
dados que ela expõe. 
  4. APIs simples, URIs de recursos mais complexos do que coleção/item/coleção.
  5. Atualização em lote para operações complexas (Inserção de Tracks em playlists).
  6. Documentação vua SWAGGER da API. 
  7. Versione suas APIs. 
  8. Uso correto dos códigos de retorno HTTP.

- Circuit Breaking (Hystrix no playlist-service)
- Uso do API Gateway - spring-cloud-gateway
- Eureka Discovery
- Ligação com a API Spotify via token de usuário
