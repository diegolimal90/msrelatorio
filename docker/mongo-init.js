db = db.getSiblingDB('fluxocaixadb');
db.createCollection('lancamento');
db.createUser(
    {
        user: "root",
        pwd: "admin",
        roles: [
            {
                role: "readWrite",
                db: "fluxocaixadb"
            }
        ]
    }
);