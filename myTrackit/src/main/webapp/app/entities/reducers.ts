import transfert from 'app/entities/transfert/transfert.reducer';
import typeAction from 'app/entities/type-action/type-action.reducer';
import staff from 'app/entities/staff/staff.reducer';
import pointFocalPointService from 'app/entities/point-focal-point-service/point-focal-point-service.reducer';
import suiviMission from 'app/entities/suivi-mission/suivi-mission.reducer';
import action from 'app/entities/action/action.reducer';
import zrosts from 'app/entities/zrosts/zrosts.reducer';
import stockPointService from 'app/entities/stock-point-service/stock-point-service.reducer';
import pointService from 'app/entities/point-service/point-service.reducer';
import monitoring from 'app/entities/monitoring/monitoring.reducer';
import requetePartenaire from 'app/entities/requete-partenaire/requete-partenaire.reducer';
import stockPartenaire from 'app/entities/stock-partenaire/stock-partenaire.reducer';
import mission from 'app/entities/mission/mission.reducer';
import detailsRequete from 'app/entities/details-requete/details-requete.reducer';
import partenaire from 'app/entities/partenaire/partenaire.reducer';
import itemVerifie from 'app/entities/item-verifie/item-verifie.reducer';
import location from 'app/entities/location/location.reducer';
import requetePointService from 'app/entities/requete-point-service/requete-point-service.reducer';
import section from 'app/entities/section/section.reducer';
import transporteur from 'app/entities/transporteur/transporteur.reducer';
import catalogue from 'app/entities/catalogue/catalogue.reducer';
import itemTransfert from 'app/entities/item-transfert/item-transfert.reducer';
import pointFocalPartenaire from 'app/entities/point-focal-partenaire/point-focal-partenaire.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  transfert,
  typeAction,
  staff,
  pointFocalPointService,
  suiviMission,
  action,
  zrosts,
  stockPointService,
  pointService,
  monitoring,
  requetePartenaire,
  stockPartenaire,
  mission,
  detailsRequete,
  partenaire,
  itemVerifie,
  location,
  requetePointService,
  section,
  transporteur,
  catalogue,
  itemTransfert,
  pointFocalPartenaire,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
