db = new Mongo().getDB("fun-mode-child");

db.createCollection("customers");
db.createCollection("products");
db.createCollection("orders");
db.createCollection("payments");
db.createCollection("categories");

db.categories.insertMany(
    [
        {name: 'Brinquedos'},
        {name: 'Escolar'},
        {name: 'DVDs Blu-ray e Livros'},
        {name: 'Vestuário Infantil'},
        {name: 'Games'},
        {name: 'Veículos'},
        {name: 'Eletrônicos'},
        {name: 'Playgrounds'},
        {name: 'Quarto e Decoração'}
    ]
);

let toys = db.categories.findOne({name: 'Brinquedos'});
let school = db.categories.findOne({name: 'Escolar'});
let culture = db.categories.findOne({name: 'DVDs Blu-ray e Livros'});
let clothes = db.categories.findOne({name: 'Vestuário Infantil'});
let games = db.categories.findOne({name: 'Games'});
let vehicles = db.categories.findOne({name: 'Veículos'});
let electronics = db.categories.findOne({name: 'Eletrônicos'});
let playgrounds = db.categories.findOne({name: 'Playgrounds'});
let decoration = db.categories.findOne({name: 'Quarto e Decoração'});

db.products.insertMany(
    [
        {
            "sku": 1,
            "name": "Boneco Dinossauro",
            "description": "Espertos como sempre, os Velociraptors são imbatíveis quando o assunto é caçar. Eles estão preparados para se juntar e formar uma equipe de caçadores invencíveis. Com suas garras e ferozes dentes, nada irá parar no caminho destes aterrorizantes dinossauros.",
            "price": 49.8,
            "categoryId": toys._id,
            "reference": "/assets/img/1.jpg",
            "referenceBig": "/assets/img/din/1.jpg",
            "quantity": 1,
            "promoted": true,
            "createdAt": new Date()
        },
        {
            "sku": 2,
            "name": "Mesinha Oficina de Criações",
            "description": "Mesinha Oficina de Criações! Para pintar, montar brinquedos, comer... De ótima qualidade e com acentos e encostos anatômicos que proporcionam maior conforto durante a brincadeira! Peças de fácil encaixe para total segurança das crianças!",
            "price": 130.2,
            "categoryId": school._id,
            "reference": "/assets/img/2.jpg",
            "referenceBig": "/assets/img/din/2.jpg",
            "quantity": 6,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 3,
            "name": "Lancheira",
            "description": "A Lancheira é ideal para acomodar os alimentos, possui garfo, colher e divisória que proporciona praticidade durante a alimentação. A tampa possui um sistema de fechamento seguro e mantém separados os talheres dos alimentos. Produzido em material moderno, que evita a alteração de gosto e odor. É resistente, design moderno e cores alegres.",
            "price": 52.9,
            "categoryId": school._id,
            "reference": "/assets/img/3.jpg",
            "referenceBig": "/assets/img/din/3.jpg",
            "quantity": 0,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 4,
            "name": "Livro Disney Winnie The Pooh - O Livro do Piano de Pooh",
            "description": "É importante que os pequenos tenham contato com a leitura desde a primeira idade. Mesmo que ainda não tenham aprendido a ler, as histórias ajudam a aguçar a curiosidade, estimulam a mente e ensinam o gosto por livros. O Livro do Piano de Pooh também é um livro musical! Vai auxiliar as crianças a desenvolverem a habilidade musical, a coordenação motora e visual! Dessa forma, vão aprender sobre notas musicais ao mesmo tempo que se divertem à beça! Brincadeira rica em aprendizado!",
            "price": 69.9,
            "categoryId": culture._id,
            "reference": "/assets/img/4.jpg",
            "referenceBig": "/assets/img/din/4.jpg",
            "quantity": 5,
            "promoted": true,
            "createdAt": new Date()
        },
        {
            "sku": 5,
            "name": "Boné Fuleco Bebê",
            "description": "Boné Fuleco Bebê – Oficial Copa do Mundo 2014, licenciado pela FIFA mascote brasileiro da Copa do Mundo. No formato da cabeça do Tatu Bola, mascote da Copa com orelhinhas na parte de cima do boné e estampa do rosto do Fuleco para ficar protegido do sol e ainda torcer muito durante os jogos. Parte de trás do boné elástico que se ajusta a cabeça do bebê.",
            "price": 29.9,
            "categoryId": clothes._id,
            "reference": "/assets/img/5.jpg",
            "referenceBig": "/assets/img/din/5.jpg",
            "quantity": 2,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 6,
            "name": "Camiseta - Infantil - Cinza",
            "description": "A Camiseta de Manga Longa da Disney deixará o seu pequeno ainda mais lindo e estiloso. Na cor mescla claro, em malha penteada, com decote redondo, possui uma estampa para enfeitar ainda mais e fazer de seu pequeno príncipe um verdadeiro anjinho. Não deixe de comprar e proteger o seu pequeno!",
            "price": 45.5,
            "categoryId": clothes._id,
            "reference": "/assets/img/6.jpg",
            "referenceBig": "/assets/img/din/6.jpg",
            "quantity": 4,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 7,
            "name": "Freakyforms Deluxe: Your Creations, Alive!",
            "description": " 3DS para povoar seu próprio planeta! Escolha entre mais de 600 combinações diferentes para completar a sua criação, como asas para voar ou rodas. As decisões que você fizer determinarão quais as habilidades do seu Formee e como ele se moverá pelo mundo.",
            "price": 79,
            "categoryId": games._id,
            "reference": "/assets/img/7.jpg",
            "referenceBig": "/assets/img/din/7.jpg",
            "quantity": 2,
            "promoted": true,
            "createdAt": new Date()
        },
        {
            "sku": 8,
            "name": "Mini Veículo Elétrico - Camaro com Controle Remoto 6V Rosa",
            "description": "Cheio de estilo, o Mini Veículo Camaro Rosa da Bandeirante vai proporcionar muita diversão para os pequenos! Com o Camaro Rosa, a sua pequena vai poder ir pra cima e pra baixo, seja sozinha, seja com a ajuda dos pais. Com o controle remoto os pais também podem participar da brincadeira, movimentando o mini veículo para todos os lados e levando a criança para todo lado.",
            "price": 999.99,
            "categoryId": vehicles._id,
            "reference": "/assets/img/8.jpg",
            "referenceBig": "/assets/img/din/8.jpg",
            "quantity": 3,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 9,
            "name": "Bicicleta Rock - Aro 12 - Azul e Vermelho",
            "description": "A Bicicleta Verden - Rock Aro 12 é ideal para as crianças que estão dando as primeiras pedaladas se divertirem muito e com segurança, pois possui quadro e garfo em aço carbono e rodinhas para pedaladas seguras! É para a garotada que tem energia de sobra e também para os momentos de lazer. Produto certificado por órgão acreditado pelo Inmetro.",
            "price": 149.49,
            "categoryId": vehicles._id,
            "reference": "/assets/img/9.jpg",
            "referenceBig": "/assets/img/din/9.jpg",
            "quantity": 9,
            "promoted": true,
            "createdAt": new Date()
        },
        {
            "sku": 10,
            "name": "Triciclo",
            "description": "As crianças passam por uma intensa fase de aprendizado durante o crescimento. Há a necessidade de treinar o reflexo, a coordenação motora, de começar a descobrir o mundo ao redor e se aventurar em novas brincadeiras e esportes diferenciados. Pedalar, além de ser uma brincadeira super divertida, vai ajudar a criança a adquirir firmeza nos movimentos das pernas, senso de direção, além de ajudar a vencer medos e a explorar tudo o que há à sua volta!",
            "price": 499.99,
            "categoryId": vehicles._id,
            "reference": "/assets/img/10.jpg",
            "referenceBig": "/assets/img/din/10.jpg",
            "quantity": 4,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 11,
            "name": "DVD Player Compacto",
            "description": "DVD Player Compacto - Disney Aviões da Tectoy! Design super compacto, tematizado com os personagens de Disney Aviões e com função Ripping que permite copiar arquivos de áudio de uma mídia para um pen drive, através da conexão USB! Possui conexão de áudio analógico e estéreo, sistema de alimentação bivolt automático é compatível com as principais mídias do mercado e tem diversas funções de reprodução: Zoom, Multiangulo, Programação, Repetição, entre outros.",
            "price": 172.3,
            "categoryId": electronics._id,
            "reference": "/assets/img/11.jpg",
            "referenceBig": "/assets/img/din/11.jpg",
            "quantity": 6,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 12,
            "name": "Flat Ball",
            "description": "Jogar bola dentro de casa sem quebrar nada e sujar as paredes é possível! Com a Flat Ball da Multikids, as mães podem ficar despreocupadas com qualquer acidente e as crianças vão se divertir muito com essa brincadeira eletrizante!",
            "price": 114.99,
            "categoryId": playgrounds._id,
            "reference": "/assets/img/12.jpg",
            "referenceBig": "/assets/img/din/12.jpg",
            "quantity": 5,
            "promoted": true,
            "createdAt": new Date()
        },
        {
            "sku": 13,
            "name": "Cama Elástica Galvanizada - 2,50 de Diâmetro",
            "description": "Deixe a brincadeira da criançada ainda mais animada com a Cama Elástica da Jundplay! Ideal para brincadeiras ao ar livre que estimulam a coordenação e movimentação das crianças, a Cama Elástica pode ser usada por crianças com mais de três anos. Estimule a agilidade dos pequenos, deixe a criatividade tomar conta da brincadeira, e gaste toda a energia da criançada nessa deliciosa brincadeira!",
            "price": 1549.99,
            "categoryId": playgrounds._id,
            "reference": "/assets/img/13.jpg",
            "referenceBig": "/assets/img/din/13.jpg",
            "quantity": 0,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 14,
            "name": "Porta Objetos Portátil",
            "description": "Com o Porta Objetos Portátil - Spider-Man da Zippy Toys a bagunça no quarto das crianças está com os dias contados! Com este Guarda Brinquedos fica mais fácil e divertido organizar os brinquedos dos pequeninos. Muito práticos, eles vem com tampa retrátil e usam o sistema Pop Up, é só abrir e pronto. Não ocupa espaço depois da brincadeira e é fácil de lavar e guardar, estando sempre disponível para mais brincadeiras! Quando montado, o guarda objetos mede aproximadamente 78 cm de altura e 43 cm de largura.",
            "price": 49.99,
            "categoryId": decoration._id,
            "reference": "/assets/img/14.jpg",
            "referenceBig": "/assets/img/din/14.jpg",
            "quantity": 2,
            "promoted": false,
            "createdAt": new Date()
        },
        {
            "sku": 15,
            "name": "Adesivo de Parede Palhaços - 56x25 cm",
            "description": "Para deixar o quarto dos seus filhos decorado, mais divertido e alegre, com vários personagens dos desenhos e filmes que ele mais gosta é fácil! Depois é só escolher a melhor posição na parede e colá-los! Para removê-los, é bem simples. Eles descolam da parede sem precisar de esforço e não deixam resíduos!",
            "price": 10.9,
            "categoryId": decoration._id,
            "reference": "/assets/img/15.jpg",
            "referenceBig": "/assets/img/din/15.jpg",
            "quantity": 2,
            "promoted": false,
            "createdAt": new Date()
        }
    ]
);

db.createUser({
    user: 'fmc',
    pwd: 'toor',
    roles: [
        {
            role: 'readWrite',
            db: 'fun-mode-child',
        },
    ],
});
